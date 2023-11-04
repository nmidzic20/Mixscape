package hr.illuminative.mixscape.ui.home.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.home.HomeViewState

interface HomeScreenMapper {
    fun toHomeViewState(
        cocktails: List<Cocktail>?,
    ): HomeViewState
}