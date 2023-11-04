package hr.illuminative.mixscape.ui.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hr.illuminative.mixscape.R
import hr.illuminative.mixscape.ui.composables.CocktailCard
import hr.illuminative.mixscape.ui.theme.spacing

@Composable
fun HomeRoute(
    onNavigateToCocktailDetails: (Int) -> Unit,
    viewModel: HomeViewModel,
) {
    val homeViewState: HomeViewState by viewModel.homeViewState.collectAsState()

    HomeScreen(
        homeViewState = homeViewState,
        onFavoriteClick = viewModel::onFavoriteClick,
        onCocktailCardClick = onNavigateToCocktailDetails,
    )
}

@Composable
fun HomeScreen(
    homeViewState: HomeViewState,
    onFavoriteClick: (String) -> Unit,
    onCocktailCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    CategoryComponent(
        homeViewState = homeViewState,
        onCocktailCardClick = onCocktailCardClick,
        onFavoriteClick = onFavoriteClick,
        title = stringResource(id = R.string.search_cocktails),
        modifier = Modifier.padding(MaterialTheme.spacing.small),
    )
}

@Composable
fun CategoryComponent(
    homeViewState: HomeViewState,
    onCocktailCardClick: (Int) -> Unit,
    onFavoriteClick: (String) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    val cocktails = homeViewState.homeCocktailViewStateList.toMutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier,
    ) {
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            },
        ) {
            Text(
                text = stringResource(id = R.string.favorites),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small),
            )
        }

        itemsIndexed(
            items = cocktails,
            key = { _, cocktail ->
                cocktail.id
            },
        ) { index, cocktail ->
            CocktailCard(
                cocktailCardViewState = cocktails[index].cocktailCardViewState,
                onClick = { onCocktailCardClick(cocktail.id.toInt()) },
                onFavoriteClick = { onFavoriteClick(cocktail.id) },
                modifier = Modifier
                    .height(179.dp)
                    .padding(MaterialTheme.spacing.small),
            )
        }
    }
}
