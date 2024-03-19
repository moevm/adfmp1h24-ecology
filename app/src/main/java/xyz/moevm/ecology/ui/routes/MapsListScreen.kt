package xyz.moevm.ecology.ui.routes

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
        if (map_.updateUserId != "test_data") {
            listOf(
                map_.name!!,
                formatter.format(parser.parse(map_.updateDatetime!!)),
                users.find({ it._id!!.id == map_.updateUserId!! })!!.login!!
            )
        }
        else{
            listOf(
                map_.name!!,
                formatter.format(parser.parse(map_.updateDatetime!!)),
                map_.updateUserId!!
            )
        }
    }
    var onRowClick = maps.map {{}}

    if (user != null)
    {
        onRowClick = maps.map {
            { navController.navigate("user/${it?.updateUserId}")}
        }
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