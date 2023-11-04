package hr.illuminative.mixscape.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hr.illuminative.mixscape.ui.theme.LightPink
import hr.illuminative.mixscape.ui.theme.LightYellow
import hr.illuminative.mixscape.ui.theme.spacing

@Composable
fun InfoItem(
    info: String,
    index: Int,
    modifier: Modifier = Modifier,
) {
    val color = if (index % 2 == 0) LightYellow else LightPink

    ElevatedCard(
        elevation = CardDefaults.cardElevation(MaterialTheme.spacing.medium),
        colors = CardDefaults.cardColors(color),
        modifier = modifier
    ) {
        Text(
            text = info,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
    }
}
