package com.example.aipetcareassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipetcareassistant.data.Pet
import com.example.aipetcareassistant.data.PetRequest
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetDetailViewModel(private val context: Context) : ViewModel() {
    private val api = ApiClient.create(context)

    private val _pet = MutableStateFlow<Pet?>(null)
    val pet: StateFlow<Pet?> = _pet

    fun loadPet(id: String) {
        viewModelScope.launch {
            _pet.value = api.getPet(id)
        }
    }

    fun updatePet(id: String, request: PetRequest) {
        viewModelScope.launch {
            _pet.value = api.updatePet(id, request)
        }
    }
}
