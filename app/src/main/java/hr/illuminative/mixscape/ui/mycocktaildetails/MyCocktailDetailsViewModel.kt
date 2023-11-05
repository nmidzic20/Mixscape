package hr.illuminative.mixscape.ui.mycocktaildetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.illuminative.mixscape.data.repository.MixscapeRepository
import hr.illuminative.mixscape.ui.mycocktaildetails.mapper.MyCocktailDetailsMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyCocktailDetailsViewModel(
    private val cocktailId: String,
    private val mixscapeRepository: MixscapeRepository,
    myCocktailDetailsMapper: MyCocktailDetailsMapper,
) : ViewModel() {

    private val initialCocktailDetailsViewState = MyCocktailDetailsViewState.EMPTY

    val cocktailDetailsViewState: StateFlow<MyCocktailDetailsViewState> =
        mixscapeRepository
            .myCocktailDetails(cocktailId)
            .map { cocktailDetails ->
                myCocktailDetailsMapper.toMyCocktailDetailsViewState(cocktailDetails)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = initialCocktailDetailsViewState,
            )

    fun onFavoriteClick(cocktailId: Int) {
        viewModelScope.launch { mixscapeRepository.toggleFavorite(cocktailId.toString()) }
    }
}
