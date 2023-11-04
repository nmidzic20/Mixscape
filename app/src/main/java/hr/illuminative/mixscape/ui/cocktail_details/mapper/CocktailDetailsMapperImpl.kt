package hr.illuminative.mixscape.ui.cocktail_details.mapper

import hr.illuminative.mixscape.model.CocktailDetails
import hr.illuminative.mixscape.ui.cocktail_details.CocktailDetailsViewState

class CocktailDetailsMapperImpl : CocktailDetailsMapper {
    override fun toCocktailDetailsViewState(cocktailDetails: CocktailDetails): CocktailDetailsViewState {
        return CocktailDetailsViewState(
            id = cocktailDetails.id,
            imageUrl = cocktailDetails.imageUrl,
            isFavorite = cocktailDetails.isFavorite,
            category = cocktailDetails.category,
            glassType = cocktailDetails.glassType,
            iba = cocktailDetails.iba,
            isAlcoholic = cocktailDetails.isAlcoholic,
            ingredients = cocktailDetails.ingredients,
            preparationInstructions = cocktailDetails.preparationInstructions,
            name = cocktailDetails.name,
        )
    }
}
