package hr.illuminative.mixscape.navigation

const val MY_COCKTAIL_DETAILS_ROUTE = "MyCocktailDetails"
const val MY_COCKTAIL_ID_KEY = "cocktailId"
const val MY_COCKTAIL_DETAILS_ROUTE_WITH_PARAMS = "$MY_COCKTAIL_DETAILS_ROUTE/{$MY_COCKTAIL_ID_KEY}"

object MyCocktailDetailDestination : MixscapeAppDestination(MY_COCKTAIL_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(cocktailId: Int): String = "$MY_COCKTAIL_DETAILS_ROUTE/$cocktailId"
}
