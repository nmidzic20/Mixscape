package hr.illuminative.mixscape.data.repository

import hr.illuminative.mixscape.data.database.DbFavoriteCocktail
import hr.illuminative.mixscape.data.database.DbMyCocktail
import hr.illuminative.mixscape.data.database.FavoriteCocktailDAO
import hr.illuminative.mixscape.data.database.MyCocktailDAO
import hr.illuminative.mixscape.data.network.CocktailService
import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.model.CocktailDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class MixscapeRepositoryImpl(
    private val cocktailService: CocktailService,
    private val favoriteCocktailDao: FavoriteCocktailDAO,
    private val myCocktailDao: MyCocktailDAO,
    private val bgDispatcher: CoroutineDispatcher,
) : MixscapeRepository {

    private val favorites = favoriteCocktailDao.favorites().map {
        it.map { dbFavoriteCocktail ->
            Cocktail(
                id = dbFavoriteCocktail.id,
                imageUrl = dbFavoriteCocktail.posterUrl,
                name = "",
                preparationInstructions = "",
                ingredients = emptyList(),
                isFavorite = true,
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1,
    )

    private val myCocktails = myCocktailDao.myCocktails().flatMapLatest { dbMyCocktailList ->
        favoriteCocktailDao.favorites()
            .map { favoriteCocktails ->
                dbMyCocktailList.map { dbMyCocktail ->
                    Cocktail(
                        id = dbMyCocktail.id,
                        imageUrl = dbMyCocktail.imageUrl,
                        name = dbMyCocktail.name,
                        preparationInstructions = dbMyCocktail.preparationInstructions,
                        ingredients = listOf(dbMyCocktail.ingredients),
                        isFavorite = favoriteCocktails.any { it.id == dbMyCocktail.id },
                    )
                }
            }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1,
    )

    override fun cocktails(cocktailName: String): SharedFlow<List<Cocktail>?> = flow {
        val cocktailResponse = cocktailService.fetchCocktailsByName(cocktailName)
        emit(cocktailResponse.cocktails)
    }.flatMapLatest { apiCocktails ->
        favoriteCocktailDao.favorites()
            .map { favoriteCocktails ->
                apiCocktails?.map { apiCocktail ->
                    apiCocktail.toCocktail(isFavorite = favoriteCocktails.any { it.id == apiCocktail.id.toInt() })
                }
            }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1,
    )

    override fun cocktailDetails(cocktailId: String): Flow<CocktailDetails> = flow {
        val cocktailResponse = cocktailService.fetchCocktailDetails(cocktailId)
        emit(cocktailResponse.cocktails.first())
    }.flatMapLatest { apiCocktailDetails ->
        favoriteCocktailDao.favorites()
            .map { favoriteCocktails ->
                apiCocktailDetails.toCocktailDetails(
                    isFavorite = favoriteCocktails.any { it.id == apiCocktailDetails.id.toInt() },
                )
            }
    }.flowOn(bgDispatcher)

    override fun favoriteCocktails(): Flow<List<Cocktail>> = favorites

    override suspend fun addCocktailToFavorites(cocktailId: String) {
        val cocktailDetails = cocktailDetails(cocktailId).first()
        val posterUrl = cocktailDetails.imageUrl
        favoriteCocktailDao.insertFavoriteCocktail(DbFavoriteCocktail(cocktailId.toInt(), posterUrl))
    }

    override suspend fun removeCocktailFromFavorites(cocktailId: String) = favoriteCocktailDao.deleteFavoriteCocktail(cocktailId.toInt())

    override suspend fun toggleFavorite(cocktailId: String) {
        val favoriteCocktails = favorites.first()
        if (favoriteCocktails.none { it.id == cocktailId.toInt() }) {
            addCocktailToFavorites(cocktailId)
        } else {
            removeCocktailFromFavorites(cocktailId)
        }
    }

    override fun myListCocktails(): Flow<List<Cocktail>> = myCocktails

    override suspend fun addCocktailToMyList(cocktail: Cocktail) {
        myCocktailDao.insertMyCocktail(DbMyCocktail(cocktail.id, cocktail.name, cocktail.ingredients.first(), cocktail.preparationInstructions, cocktail.imageUrl))
    }

    override suspend fun removeCocktailFromMyList(cocktailId: String) = myCocktailDao.deleteMyCocktail(cocktailId.toInt())

    override suspend fun updateMyCocktail(cocktail: Cocktail) {
        myCocktailDao.updateMyCocktail(DbMyCocktail(cocktail.id, cocktail.name, cocktail.ingredients.first(), cocktail.preparationInstructions, cocktail.imageUrl))
    }
}
