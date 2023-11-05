package hr.illuminative.mixscape.ui.cocktaildetails.mapper

import hr.illuminative.mixscape.model.CocktailDetails
import hr.illuminative.mixscape.ui.cocktaildetails.CocktailDetailsViewState

class CocktailDetailsMapperImpl : CocktailDetailsMapper {
    override fun toCocktailDetailsViewState(cocktailDetails: CocktailDetails?): CocktailDetailsViewState {
        if (cocktailDetails == null) {
            return CocktailDetailsViewState.EMPTY
        } else {
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
        /*when (cocktailDetails) {
            null -> CocktailDetailsViewState.EMPTY
            else -> CocktailDetailsViewState(
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
        }*/
}
