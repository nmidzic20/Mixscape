package hr.illuminative.mixscape.ui.mylist

import hr.illuminative.mixscape.ui.composables.CocktailCardViewState

data class MyListCocktailViewState(
    val id: Int,
    val cocktailCardViewState: CocktailCardViewState
)

data class MyListViewState(val myListViewStateList: List<MyListCocktailViewState>)
