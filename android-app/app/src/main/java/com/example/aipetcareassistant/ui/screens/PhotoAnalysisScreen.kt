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
fun PhotoAnalysisScreen(onDone: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Upload a photo for analysis")
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth()) {
            Text("Upload Photo")
        }
    }
}
