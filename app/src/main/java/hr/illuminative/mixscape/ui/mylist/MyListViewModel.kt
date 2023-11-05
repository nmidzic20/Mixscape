package hr.illuminative.mixscape.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.illuminative.mixscape.data.repository.MixscapeRepository
import hr.illuminative.mixscape.ui.favorites.FavoritesViewState
import hr.illuminative.mixscape.ui.favorites.mapper.FavoritesMapper
import hr.illuminative.mixscape.ui.mylist.mapper.MyListMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyListViewModel(
    private val mixscapeRepository: MixscapeRepository,
    myListScreenMapper: MyListMapper,
) : ViewModel() {

    val myListViewState: StateFlow<MyListViewState> =
        mixscapeRepository
            .myListCocktails()
            .map { cocktails ->
                myListScreenMapper.toMyListViewState(cocktails)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = MyListViewState(emptyList()),
            )

    fun onFavoriteClick(cocktailId: Int) {
        viewModelScope.launch { mixscapeRepository.toggleFavorite(cocktailId.toString()) }
    }

    fun onAddClick(cocktailId: Int) {
    }

    fun onUpdateClick(cocktailId: Int) {
    }

    fun onDeleteClick(cocktailId: Int) {
    }
}