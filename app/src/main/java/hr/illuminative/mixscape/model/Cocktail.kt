package hr.illuminative.mixscape.model

data class Cocktail(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val preparationInstructions: String,
    val imageUrl: String,
    val isFavorite: Boolean,
)
