package xyz.moevm.ecology.ui.components.user

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.moevm.ecology.data.util.translateRole

@Composable
fun RoleBadge(
    role: String,
    modifier: Modifier = Modifier
) {
    val roleText = translateRole(role)

    val color = when (role) {
        "admin" -> MaterialTheme.colorScheme.primaryContainer
        "user" -> MaterialTheme.colorScheme.background
        else -> MaterialTheme.colorScheme.error
    }

    AssistChip(
        onClick = { },
        label = { Text(roleText) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(color)
    )
}