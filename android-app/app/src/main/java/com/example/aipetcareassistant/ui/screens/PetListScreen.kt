package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.data.Pet
import com.example.aipetcareassistant.data.PetRequest
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.launch

@Composable
fun PetListScreen(onPetSelected: (String) -> Unit) {
    val context = LocalContext.current
    val api = remember { ApiClient.create(context) }
    val coroutineScope = rememberCoroutineScope()
    var pets by remember { mutableStateOf<List<Pet>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        runCatching { api.listPets() }
            .onSuccess { loaded ->
                pets = loaded
                errorMessage = null
            }
            .onFailure {
                errorMessage = "No se pudieron cargar las mascotas."
            }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Tus mascotas", style = MaterialTheme.typography.headlineSmall)

        if (isLoading) {
            CircularProgressIndicator()
            return
        }

        if (errorMessage != null) {
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }

        if (pets.isEmpty()) {
            Text(text = "Aún no tienes mascotas registradas.")
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        errorMessage = null
                        runCatching {
                            api.createPet(
                                PetRequest(
                                    name = "Mascota",
                                    species = "dog",
                                    breed = "mestizo",
                                    ageYears = 3.0,
                                    weightKg = 12.0,
                                    gender = "unknown",
                                    notes = "Mascota creada desde la app."
                                )
                            )
                        }.onSuccess {
                            pets = api.listPets()
                        }.onFailure {
                            errorMessage = "No se pudo crear la mascota demo."
                        }
                        isLoading = false
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Crear mascota demo")
            }
        } else {
            pets.forEach { pet ->
                Button(
                    onClick = { onPetSelected(pet._id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "${pet.name} (${pet.species})")
                }
            }
        }
    }
}
