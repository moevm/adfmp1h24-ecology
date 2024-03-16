package xyz.moevm.ecology.data.types

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector


data class NavItem(
    val label: Int,
    val icon: ImageVector,
    val route: String,
    val visible: Boolean = true
)

data class TopNavItem(
    val label: Int,
    val route: String
)

