package xyz.moevm.ecology.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.moevm.ecology.R

@SuppressLint("UnrememberedMutableState")
@Composable
fun ErrorScreen(
    title: String,
    message: String,
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
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = message,
            textAlign = TextAlign.Center
        )
    }
}