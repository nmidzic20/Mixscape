package hr.illuminative.mixscape.ui.cocktail_details // ktlint-disable package-name

data class CocktailDetailsViewState(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val preparationInstructions: String,
    val imageUrl: String,
    val iba: String,
    val glassType: String,
    val category: String,
    val isAlcoholic: Boolean,
    val isFavorite: Boolean,
) {
    companion object {
        val EMPTY = CocktailDetailsViewState(
            id = 0,
            name = "",
            ingredients = emptyList(),
            preparationInstructions = "",
            imageUrl = "",
            iba = "",
            glassType = "",
            category = "",
            isAlcoholic = false,
            isFavorite = false,
        )
    }
}
