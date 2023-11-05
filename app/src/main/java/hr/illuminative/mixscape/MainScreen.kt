package hr.illuminative.mixscape

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import hr.illuminative.mixscape.navigation.COCKTAIL_ID_KEY
import hr.illuminative.mixscape.navigation.CocktailDetailDestination
import hr.illuminative.mixscape.navigation.MY_COCKTAIL_ID_KEY
import hr.illuminative.mixscape.navigation.MyCocktailDetailDestination
import hr.illuminative.mixscape.navigation.NavigationItem
import hr.illuminative.mixscape.ui.cocktaildetails.CocktailDetailsRoute
import hr.illuminative.mixscape.ui.cocktaildetails.CocktailDetailsViewModel
import hr.illuminative.mixscape.ui.composables.CocktailDialog
import hr.illuminative.mixscape.ui.composables.TopAppBarLogoTitle
import hr.illuminative.mixscape.ui.favorites.FavoritesRoute
import hr.illuminative.mixscape.ui.favorites.FavoritesViewModel
import hr.illuminative.mixscape.ui.home.HomeRoute
import hr.illuminative.mixscape.ui.mycocktaildetails.MyCocktailDetailsRoute
import hr.illuminative.mixscape.ui.mycocktaildetails.MyCocktailDetailsViewModel
import hr.illuminative.mixscape.ui.mylist.MyListRoute
import hr.illuminative.mixscape.ui.mylist.MyListViewModel
import hr.illuminative.mixscape.ui.theme.spacing
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    outputDirectory: File,
    cameraExecutor: ExecutorService,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                CocktailDetailDestination.route -> {
                    false
                }
                MyCocktailDetailDestination.route -> {
                    false
                }
                else -> {
                    true
                }
            }
        }
    }
    val showBackIcon = !showBottomBar

    val showFloatingActionButton by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                NavigationItem.MyListDestination.route -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    val myListViewModel = getViewModel<MyListViewModel>()

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
                        NavigationItem.MyListDestination,
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
        floatingActionButton = {
            if (showFloatingActionButton) {
                FloatingActionButton(
                    onClick = {
                        myListViewModel.onAddClick()
                    },
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.small)
                        .padding(end = 16.dp, bottom = 16.dp),
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Cocktail")
                }
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
                    HomeRoute(
                        onNavigateToCocktailDetails = { cocktailId ->
                            val cocktailRoute = CocktailDetailDestination.createNavigationRoute(cocktailId)
                            navController.navigate(cocktailRoute)
                        },
                        viewModel = getViewModel(),
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onNavigateToCocktailDetails = { cocktailId ->
                            val cocktailRoute = CocktailDetailDestination.createNavigationRoute(cocktailId)
                            navController.navigate(cocktailRoute)
                        },
                        viewModel = getViewModel<FavoritesViewModel>(),
                    )
                }
                composable(NavigationItem.MyListDestination.route) {
                    MyListRoute(
                        onNavigateToCocktailDetails = { cocktailId ->
                            val cocktailRoute = MyCocktailDetailDestination.createNavigationRoute(cocktailId)
                            navController.navigate(cocktailRoute)
                        },
                        viewModel = getViewModel<MyListViewModel>(),
                    )
                }
                composable(
                    route = CocktailDetailDestination.route,
                    arguments = listOf(navArgument(COCKTAIL_ID_KEY) { type = NavType.IntType }),
                ) {
                    val selectedCocktailId = it.arguments?.getInt(COCKTAIL_ID_KEY) ?: throw IllegalStateException("Cocktail ID is null")
                    val cocktailDetailsViewModel: CocktailDetailsViewModel = getViewModel(parameters = {
                        parametersOf(selectedCocktailId)
                    })
                    CocktailDetailsRoute(
                        viewModel = cocktailDetailsViewModel,
                    )
                }
                composable(
                    route = MyCocktailDetailDestination.route,
                    arguments = listOf(navArgument(MY_COCKTAIL_ID_KEY) { type = NavType.IntType }),
                ) {
                    val selectedCocktailId = it.arguments?.getInt(MY_COCKTAIL_ID_KEY) ?: throw IllegalStateException("Cocktail ID is null")
                    val cocktailDetailsViewModel: MyCocktailDetailsViewModel = getViewModel(parameters = {
                        parametersOf(selectedCocktailId)
                    })
                    MyCocktailDetailsRoute(
                        viewModel = cocktailDetailsViewModel,
                    )
                }
            }

            CocktailDialog(
                isVisible = myListViewModel.isAddCocktailDialogVisible.value,
                onDismiss = { myListViewModel.onCancelAddCocktail() },
                onSave = { cocktail -> myListViewModel.onSaveAddCocktail(cocktail) },
                outputDirectory,
                cameraExecutor,
            )
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
        tint = Color.White,
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
