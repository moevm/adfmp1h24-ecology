package com.example.adfmpecology.ui.components.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import com.example.adfmpecology.data.types.AuthData

@Composable
fun AuthCard(onInput: (data: AuthData) -> Unit = {}) {
    var authData by remember {
        mutableStateOf(AuthData("", ""))
    }


    Card() {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(text = "Авторизация", modifier = Modifier.padding(5.dp))
            TextField(
                value = authData.login,
                onValueChange = { authData = authData.copy(login = it) },
                label = { Text("Логин") },
                singleLine = true,
            )
            TextField(
                value = authData.password,
                onValueChange = { authData = authData.copy(password = it) },
                label = { Text("Пароль") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp),
                onClick = { onInput(authData) }) {
                Text(text = "Войти")
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun AuthCardPreview() {
    AuthCard()
}