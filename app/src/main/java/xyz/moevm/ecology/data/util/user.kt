package xyz.moevm.ecology.data.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.moevm.ecology.R

@Composable
fun translateRole(role: String) =
    when (role) {
        "admin" -> stringResource(R.string.admin_role)
        "user" -> stringResource(R.string.user_role)
        else -> "Error"
    }