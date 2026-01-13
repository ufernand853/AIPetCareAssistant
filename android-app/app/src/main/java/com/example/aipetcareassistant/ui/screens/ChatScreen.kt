package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.R

@Composable
fun ChatScreen() {
    val (message, setMessage) = remember { mutableStateOf("") }

    val textColor = Color.White

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Chat with AI", color = textColor)
        OutlinedTextField(
            value = message,
            onValueChange = setMessage,
            label = { Text("Ask a question", color = textColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedLabelColor = textColor,
                unfocusedLabelColor = textColor,
                cursorColor = textColor,
                focusedBorderColor = textColor,
                unfocusedBorderColor = textColor.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Send")
        }
        Text(text = stringResource(id = R.string.disclaimer_short))
    }
}
