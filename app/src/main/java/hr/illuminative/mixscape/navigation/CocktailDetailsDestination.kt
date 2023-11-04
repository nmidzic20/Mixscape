package hr.illuminative.mixscape.navigation

const val COCKTAIL_DETAILS_ROUTE = "CocktailDetails"
const val COCKTAIL_ID_KEY = "movieId"
const val COCKTAIL_DETAILS_ROUTE_WITH_PARAMS = "$COCKTAIL_DETAILS_ROUTE/{$COCKTAIL_ID_KEY}"

object CocktailDetailDestination : MixscapeAppDestination(COCKTAIL_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(movieId: Int): String = "$COCKTAIL_DETAILS_ROUTE/$movieId"
}