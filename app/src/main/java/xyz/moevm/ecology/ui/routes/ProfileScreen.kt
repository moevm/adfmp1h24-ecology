package xyz.moevm.ecology.ui.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Job
import xyz.moevm.ecology.api.types.ServerAuthData
import xyz.moevm.ecology.api.types.ServerUserEditData
import xyz.moevm.ecology.ui.components.user.AuthCard
import xyz.moevm.ecology.ui.components.user.UserProfile
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userDataVM: UserDataViewModel = viewModel()
) {
    val userDataState by userDataVM.state.collectAsState()
    val authed = userDataState !== null

    var loginJob by remember {
        mutableStateOf<Job?>(null)
    }
    var fail by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(visible = authed) {
        UserProfile(
            modifier = modifier
                .fillMaxSize()
                .padding(2.dp),
            onChanged = {
                userDataVM.edit(ServerUserEditData(it.login, it.password, it.name))
            }
        )
    }

    AnimatedVisibility(visible = !authed) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AuthCard(
                onInput = {
                    loginJob = userDataVM.login(ServerAuthData(it.login, it.password))
                },
                onDev = {
                    loginJob = userDataVM.devLogin()
                },
                fail = fail
            )

        }
    }

    LaunchedEffect(loginJob) {
        if (loginJob !== null) {
            loginJob?.join()
            fail = !authed
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}