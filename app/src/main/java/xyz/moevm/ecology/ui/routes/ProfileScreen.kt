package xyz.moevm.ecology.ui.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.moevm.ecology.data.types.UserData
import xyz.moevm.ecology.ui.components.user.AuthCard
import xyz.moevm.ecology.ui.components.user.UserProfile

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    var authed by remember {
        mutableStateOf(false)
    }

    var data = UserData("login", "password", "Dmitriy", "user", 100)

    AnimatedVisibility(visible = authed) {
        UserProfile(
            data,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        )
    }

    AnimatedVisibility(visible = !authed) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AuthCard { authed = true }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}