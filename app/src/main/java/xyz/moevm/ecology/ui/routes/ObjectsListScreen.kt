package xyz.moevm.ecology.ui.routes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.seanproctor.datatable.DataColumn
import xyz.moevm.ecology.R
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.ui.components.Table


@Composable
fun ObjectsListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val rows = listOf(
        listOf("Просека", "10.02.2024", "dart_veider"),
        listOf("Вырубка", "12.11.2023", "pavel_bezpravil"),
        listOf("Лес", "6.10.2023", "nekii_ivanov_dima_1234"),
        listOf("Ядерный взрыв", "16.07.1945", "nekii_ivanov_dima_1234")
    )
    val onRowClick = listOf(
        { navController.navigate(DataSource.UserNavItems[0].route) },
        { navController.navigate(DataSource.UserNavItems[0].route) },
        { navController.navigate(DataSource.UserNavItems[0].route) },
        { navController.navigate(DataSource.UserNavItems[0].route) }
    )

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