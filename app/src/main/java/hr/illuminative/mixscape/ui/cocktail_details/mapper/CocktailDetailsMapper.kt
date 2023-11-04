package hr.illuminative.mixscape.ui.cocktail_details.mapper

import hr.illuminative.mixscape.model.CocktailDetails
import hr.illuminative.mixscape.ui.cocktail_details.CocktailDetailsViewState

interface CocktailDetailsMapper {
    fun toCocktailDetailsViewState(cocktailDetails: CocktailDetails): CocktailDetailsViewState
}