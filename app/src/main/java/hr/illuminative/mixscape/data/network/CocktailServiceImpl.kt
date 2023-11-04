package hr.illuminative.mixscape.data.network

import hr.illuminative.mixscape.data.network.model.ApiCocktailDetails
import hr.illuminative.mixscape.data.network.model.CocktailDetailsResponse
import hr.illuminative.mixscape.data.network.model.CocktailResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://www.thecocktaildb.com"
private const val API_KEY = "1"

class CocktailServiceImpl(private val client: HttpClient) : CocktailService {
    override suspend fun fetchCocktailsByName(cocktailName: String): CocktailResponse = client.get("$BASE_URL/api/json/v1/$API_KEY/search.php?s=$cocktailName").body()
    override suspend fun fetchCocktailDetails(cocktailId: String): CocktailDetailsResponse = client.get("$BASE_URL/api/json/v1/$API_KEY/lookup.php?i=$cocktailId").body()
}
