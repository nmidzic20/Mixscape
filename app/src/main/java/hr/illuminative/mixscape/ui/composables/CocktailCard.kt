package hr.illuminative.mixscape.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import hr.illuminative.mixscape.ui.theme.spacing

data class CocktailCardViewState(
    val imageUrl: String?,
    val isFavorite: Boolean,
)

@Composable
fun CocktailCard(
    cocktailCardViewState: CocktailCardViewState,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
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
                model = cocktailCardViewState.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = cocktailCardViewState.imageUrl,
                modifier = Modifier,
            )
            FavoriteButton(
                isFavorite = cocktailCardViewState.isFavorite,
                onClick = onFavoriteClick,
                modifier = Modifier
                    .size(MaterialTheme.spacing.large)
                    .align(Alignment.TopStart)
                    .padding(MaterialTheme.spacing.extraSmall),
            )
        }
    }
}
