package hr.illuminative.mixscape.ui.mycocktaildetails.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.mycocktaildetails.MyCocktailDetailsViewState

interface MyCocktailDetailsMapper {
    fun toMyCocktailDetailsViewState(cocktailDetails: Cocktail): MyCocktailDetailsViewState
}