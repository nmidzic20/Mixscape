package hr.illuminative.mixscape.ui.cocktail_details // ktlint-disable package-name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hr.illuminative.mixscape.R
import hr.illuminative.mixscape.ui.composables.FavoriteButton
import hr.illuminative.mixscape.ui.composables.IngredientItem
import hr.illuminative.mixscape.ui.theme.spacing

@Composable
fun CocktailDetailsRoute(
    viewModel: CocktailDetailsViewModel,
) {
    val cocktailDetailsViewState: CocktailDetailsViewState by viewModel.cocktailDetailsViewState.collectAsState()

    CocktailDetailsScreen(
        cocktailDetailsViewState,
        viewModel::onFavoriteClick,
    )
}

@Composable
fun CocktailDetailsScreen(
    cocktailDetailsViewState: CocktailDetailsViewState,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        CoverImage(
            cocktailDetailsViewState,
            onFavoriteClick,
            Modifier.height(303.dp),
        )
        Overview(
            cocktailDetailsViewState,
            Modifier.padding(MaterialTheme.spacing.medium),
        )
        Crew(
            cocktailDetailsViewState,
            Modifier
                .padding(MaterialTheme.spacing.medium),
        )
    }
}

@Composable
fun CoverImage(
    cocktailDetailsViewState: CocktailDetailsViewState,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = cocktailDetailsViewState.imageUrl,
            contentDescription = cocktailDetailsViewState.imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 2,
                        endY = size.height,
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
        )
        CoverImageInfo(
            cocktailDetailsViewState,
            onFavoriteClick,
            Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        )
    }
}

@Composable
fun CoverImageInfo(
    cocktailDetailsViewState: CocktailDetailsViewState,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = cocktailDetailsViewState.name,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        )
        FavoriteButton(
            isFavorite = cocktailDetailsViewState.isFavorite,
            onClick = { onFavoriteClick(cocktailDetailsViewState.id) },
            modifier = androidx.compose.ui.Modifier
                .size(MaterialTheme.spacing.large)
                .padding(MaterialTheme.spacing.extraSmall),
        )
    }
}

@Composable
fun Overview(
    cocktailDetailsViewState: CocktailDetailsViewState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.preparation_instructions),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = cocktailDetailsViewState.preparationInstructions,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun Crew(
    cocktailDetailsViewState: CocktailDetailsViewState,
    modifier: Modifier = Modifier,
) {
    val ingredientsViewStateList = cocktailDetailsViewState.ingredients

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row() {
            for (i in 0..2) {
                ingredientsViewStateList.getOrNull(i)?.let {
                    IngredientItem(
                        it,
                        Modifier
                            .weight(0.33f)
                            .padding(MaterialTheme.spacing.small),
                    )
                }
            }
        }
    }
}
