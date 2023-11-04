package hr.illuminative.mixscape.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import hr.illuminative.mixscape.navigation.navigationItems

@Composable
fun BottomBar(currentDestination: NavDestination?) {
    NavigationBar {
        navigationItems.forEachIndexed { _, navigationItem ->
            val selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true

            NavigationBarItem(
                selected = selected,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label,
                    )
                },
                onClick = {
                    navigationItem.onClick()
                },
            )
        }
    }
}
