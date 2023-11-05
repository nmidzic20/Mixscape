package hr.illuminative.mixscape.ui.mylist

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
fun MyListRoute(
    onNavigateToCocktailDetails: (Int) -> Unit,
    viewModel: MyListViewModel
) {
    val myListViewState: MyListViewState by viewModel.myListViewState.collectAsState()

    MyListScreen(
        myListViewState = myListViewState,
        onCocktailCardClick = onNavigateToCocktailDetails,
        onFavoriteClick = viewModel::onFavoriteClick,
    )
}

@Composable
fun MyListScreen(
    myListViewState: MyListViewState,
    onCocktailCardClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val myListCocktails = myListViewState.myListViewStateList.toMutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier
    ) {
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            }
        ) {
            Text(
                text = stringResource(id = R.string.my_list),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
            )
        }

        itemsIndexed(
            items = myListCocktails,
            key = { _, cocktail ->
                cocktail.id
            }
        ) { index, cocktail ->
            CocktailCard(
                cocktailCardViewState = myListCocktails[index].cocktailCardViewState,
                onClick = { onCocktailCardClick(cocktail.id) },
                onFavoriteClick = { onFavoriteClick(cocktail.id) },
                modifier = Modifier
                    .height(179.dp)
                    .padding(MaterialTheme.spacing.small)
            )
        }
    }
}