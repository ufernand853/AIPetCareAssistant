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
fun RegisterScreen(onRegistered: () -> Unit) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirm, setConfirm) = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Register")
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
        OutlinedTextField(
            value = confirm,
            onValueChange = setConfirm,
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onRegistered, modifier = Modifier.fillMaxWidth()) {
            Text("Create Account")
        }
    }
}
