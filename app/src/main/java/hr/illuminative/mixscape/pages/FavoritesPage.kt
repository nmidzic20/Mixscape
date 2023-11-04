package hr.illuminative.mixscape.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import hr.illuminative.mixscape.ui.composables.BottomBar
import hr.illuminative.mixscape.ui.composables.MainScreen
import hr.illuminative.mixscape.ui.composables.TopAppBar
import hr.illuminative.mixscape.ui.composables.TopAppBarLogoTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesPage(currentDestination: NavDestination?) {
    Scaffold(
        topBar = {
            TopAppBar({ TopAppBarLogoTitle() }, {})
        },
        bottomBar = {
            BottomBar(currentDestination)
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Text(text = "Favorites")
            }
        },
    )
}
