package hr.illuminative.mixscape.ui.cocktaildetails.mapper

import hr.illuminative.mixscape.model.CocktailDetails
import hr.illuminative.mixscape.ui.cocktaildetails.CocktailDetailsViewState

interface CocktailDetailsMapper {
    fun toCocktailDetailsViewState(cocktailDetails: CocktailDetails): CocktailDetailsViewState
}