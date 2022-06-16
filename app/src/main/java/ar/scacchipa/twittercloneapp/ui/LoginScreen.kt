package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.scacchipa.twittercloneapp.viewmodel.LoginScreenViewModel

@Composable
fun LoginScreen(
    callback: () -> Unit,
    viewModel: LoginScreenViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "Login Screen Mock",
                style = MaterialTheme.typography.titleLarge
            )
            Button(onClick = callback) {
                Text("Next Screen")
            }
        }
    }
}


