package hr.illuminative.mixscape.ui.home

import hr.illuminative.mixscape.ui.composables.CocktailCardViewState

data class HomeCocktailViewState(
    val id: String,
    val cocktailCardViewState: CocktailCardViewState,
)

data class HomeViewState(val homeCocktailViewStateList: List<HomeCocktailViewState>)

