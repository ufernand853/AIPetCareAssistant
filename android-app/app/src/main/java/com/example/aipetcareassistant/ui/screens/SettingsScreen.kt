package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.R
import com.example.aipetcareassistant.data.SettingsStore
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(onViewApiLog: () -> Unit, onLogout: () -> Unit) {
    val context = LocalContext.current
    val settingsStore = remember { SettingsStore(context) }
    val showApiHost by settingsStore.showApiHost.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Settings")
        Text(text = stringResource(id = R.string.disclaimer_long))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mostrar IP destino",
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = showApiHost,
                onCheckedChange = { enabled ->
                    coroutineScope.launch {
                        settingsStore.setShowApiHost(enabled)
                    }
                }
            )
        }
        if (showApiHost) {
            Text(text = "IP destino: ${ApiClient.baseHost}")
        }
        Button(onClick = onViewApiLog, modifier = Modifier.fillMaxWidth()) {
            Text("API Call Log")
        }
        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Logout")
        }
    }
}
