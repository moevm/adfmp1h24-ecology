package xyz.moevm.ecology.ui.components.user

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OtherProfile(id: String) {
    Text(text = id)
}

@Preview(showBackground = true)
@Composable
fun OtherProfilePreview() {
    OtherProfile("0")
}