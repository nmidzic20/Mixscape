package hr.illuminative.mixscape.ui.favorites.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.composables.CocktailCardViewState
import hr.illuminative.mixscape.ui.favorites.FavoritesCocktailViewState
import hr.illuminative.mixscape.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteCocktails: List<Cocktail>): FavoritesViewState {
        val favoritesCocktailViewState: List<FavoritesCocktailViewState> = favoriteCocktails.map { cocktail ->
            FavoritesCocktailViewState(
                cocktail.id,
                CocktailCardViewState(
                    cocktail.imageUrl,
                    cocktail.isFavorite,
                ),
            )
        }
        return FavoritesViewState(favoritesCocktailViewState)
    }
}
