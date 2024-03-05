package xyz.moevm.ecology.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.ui.routes.AboutScreen
import xyz.moevm.ecology.ui.routes.MapScreen
import xyz.moevm.ecology.ui.routes.MapsListScreen
import xyz.moevm.ecology.ui.routes.ObjectsListScreen
import xyz.moevm.ecology.ui.routes.ProfileScreen
import xyz.moevm.ecology.ui.routes.ShareScreen
import xyz.moevm.ecology.ui.routes.UsersListScreen

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