package xyz.moevm.ecology.ui.routes

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import com.seanproctor.datatable.DataColumn
import xyz.moevm.ecology.R
import xyz.moevm.ecology.data.viewmodels.MapDataViewModel
import xyz.moevm.ecology.data.viewmodels.ObjectsViewModel
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import xyz.moevm.ecology.data.viewmodels.UsersViewModel
import xyz.moevm.ecology.ui.components.Table


@SuppressLint("SimpleDateFormat")
@Composable
fun ObjectsListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mapDataVM: MapDataViewModel = viewModel(),
    objectsVM: ObjectsViewModel = viewModel(),
    userVM: UserDataViewModel = viewModel(),
    usersVM: UsersViewModel = viewModel()
) {
    // Для режимов таблицы.
    val tableModeMap by mapDataVM.tableModeMap.collectAsState()
    val errorAuthTitle = stringResource(id = R.string.not_auth_user_error_screen_1)
    val errorAuthMessage = stringResource(id = R.string.not_auth_user_error_screen_2)
    val errorNoUserTitle = stringResource(id = R.string.object_user_error_screen_1)
    val errorNoUserMessage = stringResource(id = R.string.object_user_error_screen_2)

    objectsVM.fetchObjects()
    usersVM.fetchUsers()

    val user by userVM.state.collectAsState()
    val users by usersVM.usersList.collectAsState()
    val objects by objectsVM.objectsList.collectAsState()
    val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")

    val rows = objects.map { obj->
        listOf(
            obj.name,
            formatter.format(parser.parse(obj.updateDatetime)),
            if (obj.updateUserId == "auto_generated")
                obj.updateUserId
            else if (users.isNotEmpty())
                users.find { it._id!!.id == obj.updateUserId }!!.login!!
            else if (user == null)
                stringResource(R.string.table_not_auth)
            else
                stringResource(R.string.table_loading)
        )
    }

    val onRowClick = objects.map {rowData ->
        {
            if (tableModeMap) {
                mapDataVM.setCameraPos(LatLng(rowData.center[0], rowData.center[1]))
                navController.navigate("map")
            } else if (rowData.updateUserId == "auto_generated") {
                navController.navigate("error/${errorNoUserTitle}/${errorNoUserMessage}")
            } else if (user == null) {
                navController.navigate("error/${errorAuthTitle}/${errorAuthMessage}")
            } else {
                navController.navigate("user/${rowData.updateUserId}")
            }
        }
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.table_objects),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        ) {
            Text(text = stringResource(R.string.table_mode_change))

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = tableModeMap,
                onCheckedChange = { mapDataVM.setTableModeMap(it) },
                thumbContent = if (tableModeMap) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Map,
                            contentDescription = stringResource(R.string.table_mode_map),
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription =stringResource(R.string.table_mode_user_rate),
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
        }

        Table(
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
            onRowClick = onRowClick
        )
    }
}