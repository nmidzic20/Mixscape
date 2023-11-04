package hr.illuminative.mixscape

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.illuminative.mixscape.favorites.FavoritesScreen
import hr.illuminative.mixscape.home.HomeScreen
import hr.illuminative.mixscape.navigation.COCKTAIL_ID_KEY
import hr.illuminative.mixscape.navigation.CocktailDetailDestination
import hr.illuminative.mixscape.navigation.NavigationItem
import hr.illuminative.mixscape.ui.composables.TopAppBarLogoTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                CocktailDetailDestination.route -> {
                    false
                }
                else -> {
                    true
                }
            }
        }
    }
    val showBackIcon = !showBottomBar

    Scaffold(
        topBar = {
            TopBar(
                { TopAppBarLogoTitle() },
                navigationIcon = {
                    if (showBackIcon) BackIcon(onBackClick = navController::popBackStack, Modifier.padding(6.dp))
                },
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    currentDestination = navBackStackEntry?.destination,
                )
            }
        },
    ) { padding ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding),
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeScreen()
                /*HomeRoute(
                        onNavigateToCocktailDetails = { cocktailId ->
                            val cocktailRoute = CocktailDetailDestination.createNavigationRoute(cocktailId)
                            navController.navigate(cocktailRoute)
                        },
                        viewModel = getViewModel()
                    )*/
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesScreen()
                /*FavoritesRoute(
                        onNavigateToCocktailDetails = { cocktailId ->
                            val cocktailRoute = CocktailDetailDestination.createNavigationRoute(cocktailId)
                            navController.navigate(cocktailRoute)
                        },
                        viewModel = getViewModel<FavoritesViewModel>()
                    )*/
                }
                composable(
                    route = CocktailDetailDestination.route,
                    arguments = listOf(navArgument(COCKTAIL_ID_KEY) { type = NavType.IntType }),
                ) {
                    /*val selectedCocktailId = it.arguments?.getInt(COCKTAIL_ID_KEY) ?: throw IllegalStateException("Cocktail ID is null")
                    val cocktailDetailsViewModel: CocktailDetailsViewModel = getViewModel(parameters = {
                        parametersOf(selectedCocktailId)
                    })
                    CocktailDetailsRoute(
                        viewModel = cocktailDetailsViewModel
                    )*/
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            title()
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            navigationIcon()
        },
    )
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        stringResource(R.string.back),
        modifier = modifier.clickable { onBackClick() },
    )
}

@Composable
fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.tertiary,
    ) {
        destinations.forEachIndexed { _, destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            val iconResource = if (selected) destination.selectedIconId else destination.unselectedIconId

            NavigationBarItem(
                selected = selected,
                label = {
                    Text(stringResource(destination.labelId))
                },
                icon = {
                    Icon(
                        painter = painterResource(iconResource),
                        contentDescription = stringResource(id = destination.labelId),
                    )
                },
                onClick = {
                    onNavigateToDestination(destination)
                },
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
