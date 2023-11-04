package hr.illuminative.mixscape.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.illuminative.mixscape.data.repository.MixscapeRepository
import hr.illuminative.mixscape.ui.favorites.mapper.FavoritesMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val mixscapeRepository: MixscapeRepository,
    favoritesScreenMapper: FavoritesMapper,
) : ViewModel() {

    val favoritesViewState: StateFlow<FavoritesViewState> =
        mixscapeRepository
            .favoriteCocktails()
            .map { cocktails ->
                favoritesScreenMapper.toFavoritesViewState(cocktails)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = FavoritesViewState(emptyList()),
            )

    fun onFavoriteClick(cocktailId: Int) {
        viewModelScope.launch { mixscapeRepository.toggleFavorite(cocktailId.toString()) }
    }
}
