package hr.illuminative.mixscape.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hr.illuminative.mixscape.R
import hr.illuminative.mixscape.ui.theme.Gray700
import hr.illuminative.mixscape.ui.theme.spacing
@Composable
fun MyCocktailCard(
    cocktailCardViewState: CocktailCardViewState,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        elevation = CardDefaults.cardElevation(MaterialTheme.spacing.small),
        modifier = modifier.clickable { onClick() },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cocktailCardViewState.imageUrl)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = cocktailCardViewState.imageUrl,
                modifier = Modifier,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.extraSmall),
            ) {
                FavoriteButton(
                    isFavorite = cocktailCardViewState.isFavorite,
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .size(MaterialTheme.spacing.large)
                        .padding(MaterialTheme.spacing.extraSmall),
                )
                RemoveButton(
                    onClick = onRemoveClick,
                    modifier = Modifier
                        .size(MaterialTheme.spacing.large)
                        .padding(MaterialTheme.spacing.extraSmall),
                )
            }
        }
    }
}

@Composable
fun RemoveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.drawable.ic_delete),
        contentDescription = stringResource(id = R.string.delete),
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Gray700)
            .padding(MaterialTheme.spacing.small),
    )
}

@Preview
@Composable
fun MyCocktailCardPreview() {
    MyCocktailCard(
        cocktailCardViewState = CocktailCardViewState("", false),
        onClick = { },
        onFavoriteClick = { },
        onRemoveClick = { },
    )
}
