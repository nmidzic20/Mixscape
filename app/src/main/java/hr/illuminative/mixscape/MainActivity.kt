package hr.illuminative.mixscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hr.illuminative.mixscape.navigation.BottomNavItem
import hr.illuminative.mixscape.navigation.navigationItems
import hr.illuminative.mixscape.pages.FavoritesPage
import hr.illuminative.mixscape.pages.HomePage
import hr.illuminative.mixscape.ui.theme.MixscapeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MixscapeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    navigationItems.add(
                        BottomNavItem("Home", "home", Icons.Default.Home) {
                            navController.navigate("home") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                    navigationItems.add(
                        BottomNavItem("Favorites", "favorites", Icons.Default.Favorite) {
                            navController.navigate("favorites") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomePage(currentDestination = navBackStackEntry?.destination)
                        }
                        composable("favorites") {
                            FavoritesPage(currentDestination = navBackStackEntry?.destination)
                        }
                    }
                }
            }
        }
    }
}
