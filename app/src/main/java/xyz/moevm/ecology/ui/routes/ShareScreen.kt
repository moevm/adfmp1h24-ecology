package xyz.moevm.ecology.ui.routes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import xyz.moevm.ecology.R

@Composable
fun ShareScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(15.dp, 5.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.polygon1),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )

        Button(modifier = Modifier
            .align(Alignment.End)
            .padding(top = 10.dp), onClick = { /* Do something or nothing */ }) {
            Text(text = "Поделиться")
        }
    }
}