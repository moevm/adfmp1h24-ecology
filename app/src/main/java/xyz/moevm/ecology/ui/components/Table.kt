package xyz.moevm.ecology.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState


@Composable
fun Table(
    columns: List<DataColumn>,
    rows:List<List<String>>,
    onRowClick: List<() -> Unit>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PaginatedDataTable(
            columns = columns,
            state = rememberPaginatedDataTableState(5),
            modifier = Modifier.fillMaxWidth(),
        ) {
            for (i in rows.indices) {
                row {
                    onClick = onRowClick[i]
                    for (j in rows[i].indices) {
                        cell {
                            Text(rows[i][j])
                        }
                    }
                }
            }
        }
    }
}