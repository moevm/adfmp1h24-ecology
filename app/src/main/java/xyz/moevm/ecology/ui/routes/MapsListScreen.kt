package xyz.moevm.ecology.ui.routes

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.seanproctor.datatable.DataColumn
import xyz.moevm.ecology.R
import xyz.moevm.ecology.data.viewmodels.MapsViewModel
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import xyz.moevm.ecology.data.viewmodels.UsersViewModel
import xyz.moevm.ecology.ui.components.Table


@SuppressLint("SimpleDateFormat")
@Composable
fun MapsListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mapsVM: MapsViewModel = viewModel(),
    userVM: UserDataViewModel = viewModel(),
    usersVM: UsersViewModel = viewModel()
) {
    mapsVM.fetchMaps()
    usersVM.fetchUsers()

    val users by usersVM.usersList.collectAsState()
    val maps by mapsVM.mapsList.collectAsState()
    val user by userVM.state.collectAsState()
    val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")

    val rows = maps.map { map_->
        listOf(
            map_.name,
            formatter.format(parser.parse(map_.updateDatetime)),
            if (users.isNotEmpty() && user != null && map_.updateUserId != "test_data")
                users.find { it._id!!.id == map_.updateUserId }!!.login!!
            else if (user != null && map_.updateUserId == "test_data")
                map_.updateUserId
            else if (user == null)
                stringResource(R.string.table_not_auth)
            else
                stringResource(R.string.table_loading)
        )
    }

    val onRowClick = maps.map {
            { if (user != null) navController.navigate("user/${it.updateUserId}")}
    }

    Table(
        header = stringResource(R.string.table_maps),
        columns = listOf(
            DataColumn {
                Text(stringResource(R.string.table_maps_col_1))
            },
            DataColumn(alignment = Alignment.End) {
                Text(stringResource(R.string.table_maps_col_2))
            },
            DataColumn(alignment = Alignment.End) {
                Text(stringResource(R.string.table_maps_col_3))
            }
        ),
        rows = rows,
        onRowClick = onRowClick,
        modifier = modifier
    )
}