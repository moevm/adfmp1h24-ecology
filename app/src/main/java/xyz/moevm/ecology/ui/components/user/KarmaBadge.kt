package xyz.moevm.ecology.ui.components.user

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KarmaBadge(
    karma: Int,
    modifier: Modifier = Modifier
) {
    val color = when {
        karma < 0 -> MaterialTheme.colorScheme.error
        karma in 0..10 -> MaterialTheme.colorScheme.background
        else -> MaterialTheme.colorScheme.primary
    }

    AssistChip(
        onClick = { },
        label = { Text(karma.toString()) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(color)
    )
}