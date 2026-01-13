package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.R

@Composable
fun SettingsScreen(onLogout: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Settings")
        Text(text = stringResource(id = R.string.disclaimer_long))
        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Logout")
        }
    }
}
