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
fun MapsListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val rows = listOf(
        listOf("СПБ Лес", "15.02.2024", "100"),
        listOf("Металлострой", "2.01.2024", "20"),
        listOf("Горы", "6.02.2024", "10"),
        listOf("Лес 2", "9.01.2024", "37")
    )
    val onRowClick = listOf(
        { navController.navigate(DataSource.UserNavItems[0].route) },
        { navController.navigate(DataSource.UserNavItems[0].route) },
        { navController.navigate(DataSource.UserNavItems[0].route) },
        { navController.navigate(DataSource.UserNavItems[0].route) }
    )

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