package hr.illuminative.mixscape.model

data class CocktailDetails(
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
)