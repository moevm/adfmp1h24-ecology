package xyz.moevm.ecology.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import xyz.moevm.ecology.R
import xyz.moevm.ecology.api.isInternetAvailable
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel


@Composable
fun BaseAppLayout(
    modifier: Modifier = Modifier,
    userDataVM: UserDataViewModel = viewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Для потери интернета.
    var internetLost = false
    val errorTitle = stringResource(id = R.string.connection_error_screen_1)
    val errorMessage = stringResource(id = R.string.connection_error_screen_2)

    // Проверяем наличие интернета, чтобы оповестить пользователя, что что-то не так переходом на
    // страницу специальную.
    LaunchedEffect(Unit) {
        while(true) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route

            if (!isInternetAvailable(context) && currentRoute != DataSource.errorScreenRoute) {
                navController.navigate("error/${errorTitle}/$errorMessage")
                internetLost = true
            } else if (isInternetAvailable(context) && internetLost) {
                // Если интернет вернулся, то возрващаем пользователя на страницу, на которой он был.
                internetLost = false
                userDataVM.fetchUser()
                navController.navigateUp()
            }
            delay(5000)
        }
    }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) },
        modifier = modifier
    ) { innerPadding ->
        NavHostContainer(navController, modifier = Modifier.padding(innerPadding))
    }
}