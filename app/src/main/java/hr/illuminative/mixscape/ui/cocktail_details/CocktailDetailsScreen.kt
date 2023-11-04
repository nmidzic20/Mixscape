package hr.illuminative.mixscape.ui.cocktail_details // ktlint-disable package-name

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hr.illuminative.mixscape.R
import hr.illuminative.mixscape.ui.composables.FavoriteButton
import hr.illuminative.mixscape.ui.composables.InfoItem
import hr.illuminative.mixscape.ui.theme.spacing
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

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
        PreparationInstructions(
            cocktailDetailsViewState,
            Modifier.padding(MaterialTheme.spacing.medium),
        )
        Ingredient(
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AlcoholicBadge(
                isAlcoholic = cocktailDetailsViewState.isAlcoholic,
                Modifier
                    .size(42.dp),
            )
            Text(
                text = if (cocktailDetailsViewState.isAlcoholic) stringResource(id = R.string.alcoholic) else stringResource(id = R.string.non_alcoholic),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
            )
        }
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
fun AlcoholicBadge(
    isAlcoholic: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            drawArc(
                color = if (isAlcoholic) Color.Red else Color.Blue,
                startAngle = -90f,
                360f,
                useCenter = false,
                style = Stroke(3.dp.toPx(), cap = StrokeCap.Round),
            )
        }
        Text(
            text = if (isAlcoholic) "A" else "NA",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
fun PreparationInstructions(
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
fun Ingredient(
    cocktailDetailsViewState: CocktailDetailsViewState,
    modifier: Modifier = Modifier,
) {
    val ingredientsViewStateList = cocktailDetailsViewState.ingredients

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.ingredients),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Column {
            ingredientsViewStateList.chunked(3).forEach { chunkedItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    for ((index, info) in chunkedItems.withIndex()) {
                        InfoItem(
                            info,
                            index,
                            Modifier
                                .weight(1f)
                                .padding(MaterialTheme.spacing.small),
                        )
                    }
                }
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.iba),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = cocktailDetailsViewState.iba,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.glass_type),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = cocktailDetailsViewState.glassType,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(bottom = MaterialTheme.spacing.large),
    ) {
        Text(
            text = stringResource(R.string.category),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = cocktailDetailsViewState.category,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

