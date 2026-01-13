package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.R

@Composable
fun ChatScreen() {
    val (message, setMessage) = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Chat with AI")
        OutlinedTextField(
            value = message,
            onValueChange = setMessage,
            label = { Text("Ask a question") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Send")
        }
        Text(text = stringResource(id = R.string.disclaimer_short))
    }
}
