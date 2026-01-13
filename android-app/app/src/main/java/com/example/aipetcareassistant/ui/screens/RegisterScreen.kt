package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(onRegistered: () -> Unit) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(context) }
    val authState by authViewModel.authState.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirm, setConfirm) = remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        if (authState == "success") {
            onRegistered()
        }
    }

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
        Button(
            onClick = {
                if (password == confirm) {
                    authViewModel.register(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (authState == "loading") "Creando..." else "Create Account")
        }
        if (errorMessage != null) {
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}
