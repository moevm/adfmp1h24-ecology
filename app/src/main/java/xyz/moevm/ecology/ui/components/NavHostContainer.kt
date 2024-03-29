package xyz.moevm.ecology.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.ui.routes.AboutScreen
import xyz.moevm.ecology.ui.routes.AddObjectScreen
import xyz.moevm.ecology.ui.routes.ErrorScreen
import xyz.moevm.ecology.ui.routes.MapScreen
import xyz.moevm.ecology.ui.routes.MapsListScreen
import xyz.moevm.ecology.ui.routes.ObjectsListScreen
import xyz.moevm.ecology.ui.routes.OtherProfileScreen
import xyz.moevm.ecology.ui.routes.ProfileScreen
import xyz.moevm.ecology.ui.routes.ShareScreen
import xyz.moevm.ecology.ui.routes.UsersListScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(
        navController = navController,
        startDestination = DataSource.UserNavItems[0].route,
        modifier = modifier,

        builder = {
            composable(DataSource.UserNavItems[0].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    MapScreen()
                }
            }

            composable(DataSource.UserNavItems[1].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    MapsListScreen(navController)
                }
            }

            composable(DataSource.UserNavItems[2].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ObjectsListScreen(navController)
                }
            }

            composable(DataSource.UserNavItems[3].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ProfileScreen()
                }
            }

            composable(
                DataSource.UserNavItems[4].route,
                arguments = listOf(
                    navArgument("id") { type = NavType.StringType }
                )
            ) { stack ->
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    OtherProfileScreen(
                        navController,
                        id = stack.arguments?.getString("id")!!
                    )
                }
            }

            composable(DataSource.TopNavItems[0].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    AddObjectScreen(navController)
                }
            }

            composable(DataSource.TopNavItems[1].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ShareScreen()
                }
            }

            composable(DataSource.TopNavItems[2].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    AboutScreen(navController)
                }
            }

            composable(DataSource.TopNavItems[3].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    AddObjectScreen(navController)
                }
            }

            // Еще 1 путь особый у админа.
            composable(DataSource.AdminNavItems[3].route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    UsersListScreen(navController)
                }
            }

            // Экран ошибки подключения.
            composable(
                DataSource.errorScreenRoute,
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("message") { type = NavType.StringType }
                )
            ) { stack ->
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ErrorScreen(
                        title = stack.arguments?.getString("title")!!,
                        message = stack.arguments?.getString("message")!!
                    )
                }
            }

        }
    )
}