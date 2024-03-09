package xyz.moevm.ecology.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import xyz.moevm.ecology.R
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userDataVM: UserDataViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var displayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = { navController.navigate(DataSource.TopNavItems[2].route) }
            ) {
                Icon(
                    imageVector = Icons.Filled.GpsFixed,
                    contentDescription = stringResource(R.string.add_object)
                )
            }
        },
        title = {
            TextButton(
                onClick = { navController.navigate(DataSource.TopNavItems[1].route) }
            ) {
                Text(
                    stringResource(R.string.title),
                    color = MaterialTheme.colorScheme.onPrimary, fontSize = 25.sp
                )
            }
        },
        actions = {
            IconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = { displayMenu = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.settings)
                )
            }

            DropdownMenu(expanded = displayMenu, onDismissRequest = { displayMenu = false }) {
                DropdownMenuItem(
                    onClick = { navController.navigate(DataSource.TopNavItems[1].route) },
                    text = {
                        Text(stringResource(R.string.menu_about))
                    }
                )

                DropdownMenuItem(
                    onClick = { userDataVM.logout() },
                    text = {
                        Text(stringResource(R.string.menu_exit))
                    }
                )
            }
        }
    )
}