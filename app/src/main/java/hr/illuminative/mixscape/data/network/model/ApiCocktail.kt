package hr.illuminative.mixscape.data.network.model

import hr.illuminative.mixscape.model.Cocktail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailResponse(
    @SerialName("drinks")
    val cocktails: List<ApiCocktail>?,
)

@Serializable
data class ApiCocktail(
    @SerialName("idDrink")
    val id: String,
    @SerialName("strDrink")
    val name: String,
    @SerialName("strIngredient1")
    val ingredient1: String?,
    @SerialName("strIngredient2")
    val ingredient2: String?,
    @SerialName("strIngredient3")
    val ingredient3: String?,
    @SerialName("strIngredient4")
    val ingredient4: String?,
    @SerialName("strIngredient5")
    val ingredient5: String?,
    @SerialName("strIngredient6")
    val ingredient6: String?,
    @SerialName("strIngredient7")
    val ingredient7: String?,
    @SerialName("strIngredient8")
    val ingredient8: String?,
    @SerialName("strIngredient9")
    val ingredient9: String?,
    @SerialName("strIngredient10")
    val ingredient10: String?,
    @SerialName("strIngredient11")
    val ingredient11: String?,
    @SerialName("strIngredient12")
    val ingredient12: String?,
    @SerialName("strIngredient13")
    val ingredient13: String?,
    @SerialName("strIngredient14")
    val ingredient14: String?,
    @SerialName("strIngredient15")
    val ingredient15: String?,
    @SerialName("strInstructions")
    val preparationInstructions: String?,
    @SerialName("strDrinkThumb")
    val imgPath: String?,
) {
    fun toCocktail(isFavorite: Boolean) = Cocktail(
        id = id.toInt(),
        name = name,
        ingredients = listOfNotNull(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9, ingredient10, ingredient11, ingredient12, ingredient13, ingredient14, ingredient15),
        preparationInstructions = preparationInstructions ?: "Not provided",
        imageUrl = "$imgPath",
        isFavorite = isFavorite,
    )
}
