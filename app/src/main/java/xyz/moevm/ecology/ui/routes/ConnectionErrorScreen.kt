package xyz.moevm.ecology.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import xyz.moevm.ecology.R
import xyz.moevm.ecology.api.isInternetAvailable

@SuppressLint("UnrememberedMutableState")
@Composable
fun ConnectionErrorScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(15.dp, 5.dp)
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Cancel,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(50.dp)
        )

        Text(
            text = stringResource(id = R.string.connection_error_screen_1),
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.connection_error_screen_2),
            textAlign = TextAlign.Center
        )
    }
}