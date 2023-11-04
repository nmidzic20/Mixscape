package hr.illuminative.mixscape.data.network

import hr.illuminative.mixscape.data.network.model.ApiCocktailDetails
import hr.illuminative.mixscape.data.network.model.CocktailDetailsResponse
import hr.illuminative.mixscape.data.network.model.CocktailResponse

interface CocktailService {
    suspend fun fetchCocktailsByName(name: String): CocktailResponse

    suspend fun fetchCocktailDetails(id: String): CocktailDetailsResponse
}
