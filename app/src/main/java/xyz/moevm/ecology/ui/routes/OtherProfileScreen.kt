package xyz.moevm.ecology.ui.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.navigation.NavHostController
import xyz.moevm.ecology.api.types.ServerUserEditData
import xyz.moevm.ecology.data.types.UserProfileData
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import xyz.moevm.ecology.data.viewmodels.UsersViewModel
import xyz.moevm.ecology.ui.components.user.KarmaBadge
import xyz.moevm.ecology.ui.components.user.RoleBadge

@Composable
fun OtherProfileScreen(
    navController: NavHostController,
    id: String,
    modifier: Modifier = Modifier,
    usersVM: UsersViewModel = viewModel(),
    userDataVM: UserDataViewModel = viewModel()
) {
    usersVM.fetchCurrentUser(id)

    val self by userDataVM.state.collectAsState()
    val currentUser by usersVM.currentUser.collectAsState()

    var edited by remember {
        mutableStateOf(false)
    }

    val isAdmin by remember {
        mutableStateOf(self?.role == "admin")
    }

    // Если пользователь вышел из аккаунта на странице оценки другого пользователя.
    LaunchedEffect(self) {
        if (self == null) {
            navController.navigateUp()
        }
    }

    AnimatedVisibility(visible = (currentUser !== null && self != null)) {
        if (currentUser === null) return@AnimatedVisibility

        var profileData by remember {
            mutableStateOf(
                UserProfileData(
                    currentUser!!.login!!,
                    currentUser!!.password!!,
                    currentUser!!.name!!,
                    currentUser!!.karma!!,
                    currentUser!!.role!!,
                    currentUser?._id?.id
                )
            )
        }

        if (profileData.id != currentUser?._id?.id) {
            profileData = UserProfileData(
                currentUser!!.login!!,
                currentUser!!.password!!,
                currentUser!!.name!!,
                currentUser!!.karma!!,
                currentUser!!.role!!,
                currentUser?._id?.id
            )
        }

        Card(modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Absolute.SpaceAround,
                Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "user icon",
                    modifier = Modifier.size(200.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(verticalArrangement = Arrangement.Center) {
                    RoleBadge(role = profileData.role, Modifier.padding(start = 5.dp))

                    Row {
                        Text("Карма", modifier = Modifier.align(Alignment.CenterVertically))
                        KarmaBadge(
                            karma = currentUser!!.karma!!,
                            Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(15.dp, 5.dp)
                    .fillMaxWidth()
            ) {

                TextField(
                    readOnly = !isAdmin,
                    value = profileData.login,
                    label = { Text("Логин") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        profileData = profileData.copy(login = it)
                        edited = true
                    }
                )

                if (isAdmin)
                    TextField(
                        readOnly = !isAdmin,
                        value = profileData.password,
                        label = { Text("Пароль") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            profileData = profileData.copy(password = it)
                            edited = true
                        }
                    )

                TextField(
                    readOnly = !isAdmin,
                    value = profileData.name,
                    label = { Text("Имя") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        profileData = profileData.copy(name = it)
                        edited = true
                    }
                )

                AnimatedVisibility(
                    visible = edited && isAdmin,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 10.dp)
                ) {
                    Button(
                        onClick = {
                            usersVM.editCurrentUser(
                                ServerUserEditData(
                                    profileData.login, profileData.password, profileData.name
                                )
                            )
                            edited = false
                        }
                    ) {
                        Text(text = "Сохранить")
                    }
                }

                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { usersVM.updateCurrentUserKarma(self!!._id!!.id!!, 1) }
                    ) {
                        Text(text = "Чел харош!")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { usersVM.updateCurrentUserKarma(self!!._id!!.id!!, -1) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = "Запостил кринж!")
                    }
                }
            }
        }
    }
}