package hr.illuminative.mixscape.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.illuminative.mixscape.data.repository.MixscapeRepository
import hr.illuminative.mixscape.ui.home.mapper.HomeScreenMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mixscapeRepository: MixscapeRepository,
    homeScreenMapper: HomeScreenMapper,
) : ViewModel() {

    private val cocktailName = MutableStateFlow("")

    val homeViewState: StateFlow<HomeViewState> =
        cocktailName
            .flatMapLatest { cocktailNameSearchword ->
                mixscapeRepository.cocktails(cocktailNameSearchword)
                    .map { cocktails ->
                        homeScreenMapper.toHomeViewState(
                            cocktails,
                        )
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = HomeViewState(emptyList()),
            )

    fun onFavoriteClick(cocktailId: String) {
        viewModelScope.launch { mixscapeRepository.toggleFavorite(cocktailId) }
    }

    fun onSearchClick(searchword: String) {
        cocktailName.value = searchword
    }
}
