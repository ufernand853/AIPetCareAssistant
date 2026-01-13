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
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegister: () -> Unit) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Login")
        OutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password") },
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
