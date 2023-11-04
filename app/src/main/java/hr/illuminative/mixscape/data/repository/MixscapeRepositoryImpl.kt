package hr.illuminative.mixscape.data.repository

import hr.illuminative.mixscape.data.database.DbFavoriteCocktail
import hr.illuminative.mixscape.data.database.FavoriteCocktailDAO
import hr.illuminative.mixscape.data.network.CocktailService
import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.model.CocktailDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class MixscapeRepositoryImpl(
    private val cocktailService: CocktailService,
    private val cocktailDao: FavoriteCocktailDAO,
    private val bgDispatcher: CoroutineDispatcher
) : MixscapeRepository {

    private val favorites = cocktailDao.favorites().map {
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
    override fun cocktails(cocktailName: String): Flow<List<Cocktail>> = flow {
        val cocktailResponse = cocktailService.fetchCocktailsByName(cocktailName)
        emit(cocktailResponse.cocktails)
    }.flatMapLatest { apiCocktails ->
        cocktailDao.favorites()
            .map { favoriteCocktails ->
                apiCocktails.map { apiCocktail ->
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
        cocktailDao.favorites()
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
        cocktailDao.insertFavoriteCocktail(DbFavoriteCocktail(cocktailId.toInt(), posterUrl))
    }

    override suspend fun removeCocktailFromFavorites(cocktailId: String) = cocktailDao.deleteFavoriteCocktail(cocktailId.toInt())

    override suspend fun toggleFavorite(cocktailId: String) {
        val favoriteCocktails = favorites.first()
        if (favoriteCocktails.none { it.id == cocktailId.toInt() })
            addCocktailToFavorites(cocktailId)
        else
            removeCocktailFromFavorites(cocktailId)
    }
}