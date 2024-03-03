package com.example.adfmpecology.data.types

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector


data class NavItem(
    val label: Int,
    val icon: ImageVector,
    val route: String
)
