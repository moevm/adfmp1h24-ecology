package xyz.moevm.ecology

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import xyz.moevm.ecology.ui.components.BaseAppLayout
import xyz.moevm.ecology.ui.theme.ADFMPEcologyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDataViewModel: UserDataViewModel =
            ViewModelProvider(this)[UserDataViewModel::class.java]

        lifecycleScope.launch {
            userDataViewModel.fetchUser()
        }

        setContent {
            ADFMPEcologyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BaseAppLayout()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BaseAppPreview() {
    ADFMPEcologyTheme {
        BaseAppLayout()
    }
}