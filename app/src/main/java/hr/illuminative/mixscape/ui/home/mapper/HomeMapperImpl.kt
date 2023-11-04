package hr.illuminative.mixscape.ui.home.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.composables.CocktailCardViewState
import hr.illuminative.mixscape.ui.home.HomeCocktailViewState
import hr.illuminative.mixscape.ui.home.HomeViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeViewState(
        cocktails: List<Cocktail>?,
    ): HomeViewState {
        if (cocktails == null) {
            return HomeViewState(emptyList())
        }

        val _cocktails: List<HomeCocktailViewState> = cocktails.map { cocktail ->
            HomeCocktailViewState(
                id = cocktail.id.toString(),
                cocktailCardViewState = CocktailCardViewState(
                    imageUrl = cocktail.imageUrl,
                    isFavorite = cocktail.isFavorite,
                ),
            )
        }
        return HomeViewState(_cocktails)
    }
}
