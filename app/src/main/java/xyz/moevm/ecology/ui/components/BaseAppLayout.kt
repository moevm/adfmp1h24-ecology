package xyz.moevm.ecology.ui.components

import android.location.Location
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import xyz.moevm.ecology.api.isInternetAvailable
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import kotlin.math.pow


@Composable
fun BaseAppLayout(
    modifier: Modifier = Modifier,
    userDataVM: UserDataViewModel = viewModel()
) {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    val context = LocalContext.current
    var internetLost = false

    // Проверяем наличие интернета, чтобы оповестить пользователя, что что-то не так переходом на
    // страницу специальную.
    LaunchedEffect(Unit) {
        while(true) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route

            if (!isInternetAvailable(context) && currentRoute != DataSource.connectionErrorScreenRoute) {
                navController.navigate(DataSource.connectionErrorScreenRoute)
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