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
import xyz.moevm.ecology.data.viewmodels.ObjectsViewModel
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import xyz.moevm.ecology.data.viewmodels.UsersViewModel
import xyz.moevm.ecology.ui.components.Table


@Composable
fun ObjectsListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    objectsVM: ObjectsViewModel = viewModel(),
    userVM: UserDataViewModel = viewModel(),
    usersVM: UsersViewModel = viewModel()
) {
    objectsVM.fetchObjects()

    val user by userVM.state.collectAsState()

    usersVM.fetchUsers()
    val users by usersVM.usersList.collectAsState()



    val objects by objectsVM.objectsList.collectAsState()
    val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
    val rows = objects.map { obj->
        if (obj.updateUserId != "auto_generated") {
            listOf(
                obj.name!!,
                formatter.format(parser.parse(obj.updateDatetime!!)),
                users.find({ it._id!!.id == obj.updateUserId!! })!!.login!!
            )
        }
        else{
            listOf(
                obj.name!!,
                formatter.format(parser.parse(obj.updateDatetime!!)),
                obj.updateUserId!!
            )
        }
    }

    var onRowClick = objects.map {{}}

    if (user != null) {
        onRowClick = objects.map {
            { navController.navigate("user/${it?.updateUserId}")}
        }
    }


    Table(
        header = stringResource(R.string.table_objects),
        columns = listOf(
            DataColumn {
                Text(stringResource(R.string.table_objects_col_1))
            },
            DataColumn(alignment = Alignment.End) {
                Text(stringResource(R.string.table_objects_col_2))
            },
            DataColumn(alignment = Alignment.End) {
                Text(stringResource(R.string.table_objects_col_3))
            }
        ),
        rows = rows,
        onRowClick = onRowClick,
        modifier = modifier
    )
}