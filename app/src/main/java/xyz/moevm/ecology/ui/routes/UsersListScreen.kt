package xyz.moevm.ecology.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.seanproctor.datatable.DataColumn
import xyz.moevm.ecology.R
import xyz.moevm.ecology.data.util.translateRole
import xyz.moevm.ecology.data.viewmodels.UsersViewModel
import xyz.moevm.ecology.ui.components.Table


@Composable
fun UsersListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    usersVM: UsersViewModel = viewModel()
) {
    usersVM.fetchUsers()

    val users by usersVM.usersList.collectAsState()

    val rows = users.map {
        listOf(
            it.login!!,
            translateRole(it.role!!),
            it.karma!!.toString()
        )
    }
    val onRowClick = users.map {
        { navController.navigate("user/${it._id?.id}") }
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.table_users),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Table(
            columns = listOf(
                DataColumn {
                    Text(stringResource(R.string.table_users_col_1))
                },
                DataColumn(alignment = Alignment.End) {
                    Text(stringResource(R.string.table_users_col_2))
                },
                DataColumn(alignment = Alignment.End) {
                    Text(stringResource(R.string.table_users_col_3))
                }
            ),
            rows = rows,
            onRowClick = onRowClick
        )
    }
}