package hr.illuminative.mixscape.ui.favorites

import hr.illuminative.mixscape.ui.composables.CocktailCardViewState

data class FavoritesCocktailViewState(
    val id: Int,
    val cocktailCardViewState: CocktailCardViewState
)

data class FavoritesViewState(val favoritesCocktailViewStateList: List<FavoritesCocktailViewState>)
