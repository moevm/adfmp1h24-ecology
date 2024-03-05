package xyz.moevm.ecology.ui.routes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.seanproctor.datatable.DataColumn
import xyz.moevm.ecology.R
import xyz.moevm.ecology.ui.components.Table


@Composable
fun UsersListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val rows = listOf(
        listOf("nekii_ivanov_dima_1234", "admin", "100000"),
        listOf("pavel_bezpravil", "user", "120"),
        listOf("dart_veider", "user", "-100"),
        listOf("moevm", "god", "5000")
    )
    val onRowClick = listOf(
        { },
        { },
        { },
        { }
    )

    Table(
        header = stringResource(R.string.table_users),
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
        onRowClick = onRowClick,
        modifier = modifier
    )
}