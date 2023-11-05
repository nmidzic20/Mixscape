package hr.illuminative.mixscape.navigation

import hr.illuminative.mixscape.R

const val HOME_ROUTE = "Home"
const val FAVORITES_ROUTE = "Favorites"
const val MY_LIST_ROUTE = "My List"

sealed class NavigationItem(
    override val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val labelId: Int,
) : MixscapeAppDestination(route) {
    object HomeDestination : NavigationItem(
        route = HOME_ROUTE,
        selectedIconId = R.drawable.ic_home_filled,
        unselectedIconId = R.drawable.ic_home_outlined,
        labelId = R.string.home,
    )
    object FavoritesDestination : NavigationItem(
        route = FAVORITES_ROUTE,
        selectedIconId = R.drawable.ic_favorite_filled,
        unselectedIconId = R.drawable.ic_favorite_outlined,
        labelId = R.string.favorites,
    )
    object MyListDestination : NavigationItem(
        route = MY_LIST_ROUTE,
        selectedIconId = R.drawable.ic_my_list_filled,
        unselectedIconId = R.drawable.ic_my_list_outlined,
        labelId = R.string.my_list,
    )
}