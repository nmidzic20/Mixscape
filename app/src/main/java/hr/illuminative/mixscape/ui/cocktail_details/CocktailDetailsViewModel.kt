package hr.illuminative.mixscape.ui.cocktail_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.illuminative.mixscape.data.repository.MixscapeRepository
import hr.illuminative.mixscape.ui.cocktail_details.mapper.CocktailDetailsMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CocktailDetailsViewModel(
    private val cocktailId: String,
    private val mixscapeRepository: MixscapeRepository,
    cocktailDetailsMapper: CocktailDetailsMapper,
) : ViewModel() {

    private val initialCocktailDetailsViewState = CocktailDetailsViewState.EMPTY

    val cocktailDetailsViewState: StateFlow<CocktailDetailsViewState> =
        mixscapeRepository
            .cocktailDetails(cocktailId)
            .map { cocktailDetails ->
                cocktailDetailsMapper.toCocktailDetailsViewState(cocktailDetails)
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
