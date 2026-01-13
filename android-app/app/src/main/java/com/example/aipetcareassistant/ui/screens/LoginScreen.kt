package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegister: () -> Unit) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val textColor = MaterialTheme.colorScheme.onBackground

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Login", color = textColor)
        OutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text("Email", color = textColor) },
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
        OutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password", color = textColor) },
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
        Button(onClick = onLoginSuccess, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
        Button(onClick = onRegister, modifier = Modifier.fillMaxWidth()) {
            Text("Go to Register")
        }
    }
}
