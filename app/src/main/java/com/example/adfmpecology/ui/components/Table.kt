package com.example.adfmpecology.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adfmpecology.R
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState


@Composable
fun Table(
    header: String,
    columns: List<DataColumn>,
    rows:List<List<String>>,
    onRowClick: List<() -> Unit>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp).fillMaxWidth()
        ) {
            Text(text = header, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = stringResource(R.string.filter)
                )
            }
        }

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