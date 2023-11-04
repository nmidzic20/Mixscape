package hr.illuminative.mixscape.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

var navigationItems = mutableListOf<BottomNavItem>()
