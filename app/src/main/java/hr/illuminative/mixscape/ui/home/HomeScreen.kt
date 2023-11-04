package hr.illuminative.mixscape.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
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
        onSearchCocktail = viewModel::onSearchClick,
    )
}

@Composable
fun HomeScreen(
    homeViewState: HomeViewState,
    onFavoriteClick: (String) -> Unit,
    onCocktailCardClick: (Int) -> Unit,
    onSearchCocktail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        SearchBarField(onSearchCocktail)
        CocktailsComponent(
            homeViewState = homeViewState,
            onCocktailCardClick = onCocktailCardClick,
            onFavoriteClick = onFavoriteClick,
            title = stringResource(id = R.string.search_cocktails),
            modifier = Modifier.padding(MaterialTheme.spacing.small),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarField(onSearchCocktail: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = { query ->
                active = false
                onSearchCocktail(query)
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Search cocktails by name") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (active) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                                onSearchCocktail(text)
                            }
                        },
                    )
                }
            },
        ) {
            val suggestions = listOf("Margarita", "Zorro", "50/50")

            suggestions.forEach { suggestion ->
                ListItem(
                    headlineContent = { Text(suggestion) },
                    leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                    modifier = Modifier
                        .clickable {
                            text = suggestion
                            active = false
                            onSearchCocktail(text)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
        }
    }
}

@Composable
fun CocktailsComponent(
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
                text = title,
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
