package hr.illuminative.mixscape.ui.mylist.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.composables.CocktailCardViewState
import hr.illuminative.mixscape.ui.mylist.MyListCocktailViewState
import hr.illuminative.mixscape.ui.mylist.MyListViewState

class MyListMapperImpl : MyListMapper {
    override fun toMyListViewState(myListCocktails: List<Cocktail>): MyListViewState {
        val myListCocktailsViewState: List<MyListCocktailViewState> = myListCocktails.map { cocktail ->
            MyListCocktailViewState(
                cocktail.id,
                CocktailCardViewState(
                    cocktail.imageUrl,
                    cocktail.isFavorite,
                ),
            )
        }
        return MyListViewState(myListCocktailsViewState)
    }
}