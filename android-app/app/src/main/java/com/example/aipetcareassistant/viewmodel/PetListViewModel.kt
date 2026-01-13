package com.example.aipetcareassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipetcareassistant.data.Pet
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetListViewModel(private val context: Context) : ViewModel() {
    private val api = ApiClient.create(context)

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    fun loadPets() {
        viewModelScope.launch {
            _pets.value = api.listPets()
        }
    }
}
