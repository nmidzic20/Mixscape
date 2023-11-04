package hr.illuminative.mixscape.ui.favorites.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.favorites.FavoritesViewState

interface FavoritesMapper {
    fun toFavoritesViewState(favoriteCocktails: List<Cocktail>): FavoritesViewState
}