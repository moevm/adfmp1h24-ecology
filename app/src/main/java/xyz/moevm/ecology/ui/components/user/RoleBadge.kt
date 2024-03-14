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
fun RoleBadge(
    role: String,
    modifier: Modifier = Modifier
) {
    val roleText = when (role) {
        "admin" -> "Администратор"
        "user" -> "Пользователь"
        else -> role
    }

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