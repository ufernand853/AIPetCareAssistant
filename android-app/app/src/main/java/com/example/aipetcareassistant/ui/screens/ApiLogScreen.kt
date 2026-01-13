package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.data.ApiCallLogEntry
import com.example.aipetcareassistant.data.ApiCallLogStore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ApiLogScreen(onBack: () -> Unit) {
    val entries = ApiCallLogStore.entries
    val formatter = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "API Call Log", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { ApiCallLogStore.clear() }) {
                Text(text = "Clear")
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(entries) { entry ->
                ApiLogEntryCard(entry = entry, formatter = formatter)
            }
            if (entries.isEmpty()) {
                item {
                    Text(text = "No API calls recorded yet.")
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Back")
        }
    }
}

@Composable
private fun ApiLogEntryCard(entry: ApiCallLogEntry, formatter: SimpleDateFormat) {
    val timestamp = formatter.format(Date(entry.timestampMillis))
    val statusLabel = if (entry.statusCode == -1) "Error" else entry.statusCode.toString()
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "${entry.method} ${entry.url}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: $statusLabel", style = MaterialTheme.typography.bodySmall)
            Text(text = "Time: ${entry.durationMs}ms • $timestamp", style = MaterialTheme.typography.bodySmall)
        }
    }
}
