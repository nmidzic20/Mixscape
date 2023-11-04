package hr.illuminative.mixscape.navigation

const val COCKTAIL_DETAILS_ROUTE = "CocktailDetails"
const val COCKTAIL_ID_KEY = "cocktailId"
const val COCKTAIL_DETAILS_ROUTE_WITH_PARAMS = "$COCKTAIL_DETAILS_ROUTE/{$COCKTAIL_ID_KEY}"

object CocktailDetailDestination : MixscapeAppDestination(COCKTAIL_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(cocktailId: Int): String = "$COCKTAIL_DETAILS_ROUTE/$cocktailId"
}
