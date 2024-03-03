package com.example.adfmpecology.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.adfmpecology.data.DataSource
import com.example.adfmpecology.ui.routes.AboutScreen
import com.example.adfmpecology.ui.routes.MapScreen
import com.example.adfmpecology.ui.routes.MapsListScreen
import com.example.adfmpecology.ui.routes.ObjectsListScreen
import com.example.adfmpecology.ui.routes.ProfileScreen
import com.example.adfmpecology.ui.routes.ShareScreen
import com.example.adfmpecology.ui.routes.UsersListScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DataSource.UserNavItems[0].route,
        modifier = modifier,

        builder = {
            composable(DataSource.UserNavItems[0].route) {
                MapScreen()
            }

            composable(DataSource.UserNavItems[1].route) {
                MapsListScreen(navController)
            }

            composable(DataSource.UserNavItems[2].route) {
                ObjectsListScreen(navController)
            }

            composable(DataSource.UserNavItems[3].route) {
                ProfileScreen()
            }

            composable(DataSource.TopNavItems[0].route) {
                MapScreen()
            }

            composable(DataSource.TopNavItems[1].route) {
                AboutScreen(navController)
            }

            composable(DataSource.TopNavItems[2].route) {
                ShareScreen(navController)
            }

            // Еще 1 путь особый у админа.
            composable(DataSource.AdminNavItems[3].route) {
                UsersListScreen(navController)
            }
        }
    )
}