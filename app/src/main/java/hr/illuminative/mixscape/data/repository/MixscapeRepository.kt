package hr.illuminative.mixscape.data.repository

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.model.CocktailDetails
import kotlinx.coroutines.flow.Flow

interface MixscapeRepository {
    fun cocktails(cocktailName: String): Flow<List<Cocktail>?>
    fun cocktailDetails(cocktailId: String): Flow<CocktailDetails?>
    fun myCocktailDetails(cocktailId: String): Flow<Cocktail>
    fun favoriteCocktails(): Flow<List<Cocktail>>
    suspend fun addCocktailToFavorites(cocktailId: String)
    suspend fun removeCocktailFromFavorites(cocktailId: String)
    suspend fun toggleFavorite(cocktailId: String)

    fun myListCocktails(): Flow<List<Cocktail>>
    suspend fun addCocktailToMyList(cocktail: Cocktail)
    suspend fun removeCocktailFromMyList(cocktailId: String)
    suspend fun updateMyCocktail(cocktail: Cocktail)
}