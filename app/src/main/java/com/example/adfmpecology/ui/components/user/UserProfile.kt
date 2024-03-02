package com.example.adfmpecology.ui.components.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class UserProfileData(var login: String, var password: String, var name: String)
data class UserData(
    val login: String, val password: String, val name: String, val role: String, val karma: Int
)

@Composable
fun UserProfile(
    data: UserData,
    modifier: Modifier = Modifier,
    onChanged: (data: UserProfileData) -> Unit = {},
) {
    var profileData by remember {
        mutableStateOf(UserProfileData(data.login, data.password, data.name))
    }

    Card(modifier) {
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
                AssistChip(onClick = { /*TODO*/ }, label = { Text(data.role) })
                Row {
                    Text("Карма", modifier = Modifier.align(Alignment.CenterVertically))
                    AssistChip(
                        onClick = { /*TODO*/ },
                        label = { Text(data.karma.toString()) },
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
                value = profileData.login,
                onValueChange = { profileData.login = it },
                label = { Text("Логин") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = profileData.password,
                onValueChange = { profileData.password = it },
                label = { Text("Пароль") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = profileData.name,
                onValueChange = { profileData.name = it },
                label = { Text("Имя") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp), onClick = { }) {
                Text(text = "Сохранить")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfile(
        data = UserData("login", "password", "Dmitriy", "user", 100)
    )
}