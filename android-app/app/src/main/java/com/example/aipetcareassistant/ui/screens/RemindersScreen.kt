package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RemindersScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Reminders")
        Text(text = "No reminders yet")
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Add Reminder")
        }
    }
}
