package hr.illuminative.mixscape.ui.mylist.mapper

import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.mylist.MyListViewState

interface MyListMapper {
    fun toMyListViewState(myListCocktails: List<Cocktail>): MyListViewState
}
