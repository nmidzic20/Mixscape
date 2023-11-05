package hr.illuminative.mixscape.ui.mycocktaildetails.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.mycocktaildetails.MyCocktailDetailsViewState

class MyCocktailDetailsMapperImpl : MyCocktailDetailsMapper {
    override fun toMyCocktailDetailsViewState(cocktailDetails: Cocktail): MyCocktailDetailsViewState {
        return MyCocktailDetailsViewState(
            id = cocktailDetails.id,
            imageUrl = cocktailDetails.imageUrl,
            isFavorite = cocktailDetails.isFavorite,
            category = "",
            glassType = "",
            iba = "",
            isAlcoholic = false,
            ingredients = cocktailDetails.ingredients,
            preparationInstructions = cocktailDetails.preparationInstructions,
            name = cocktailDetails.name,
        )
    }

}
