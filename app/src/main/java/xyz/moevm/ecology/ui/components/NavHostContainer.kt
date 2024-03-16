package xyz.moevm.ecology.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.descriptors.StructureKind
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.ui.routes.AboutScreen
import xyz.moevm.ecology.ui.routes.AddObjectScreen
import xyz.moevm.ecology.ui.routes.MapScreen
import xyz.moevm.ecology.ui.routes.MapsListScreen
import xyz.moevm.ecology.ui.routes.ObjectsListScreen
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
                    ShareScreen(navController)
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
        }
    )
}