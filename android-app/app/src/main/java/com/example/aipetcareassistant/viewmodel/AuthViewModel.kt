package com.example.aipetcareassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipetcareassistant.data.AuthRequest
import com.example.aipetcareassistant.data.TokenStore
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val context: Context) : ViewModel() {
    private val api = ApiClient.create(context)
    private val tokenStore = TokenStore(context)

    private val _authState = MutableStateFlow("idle")
    val authState: StateFlow<String> = _authState
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = "loading"
            _errorMessage.value = null
            try {
                val response = api.login(AuthRequest(email, password))
                tokenStore.saveToken(response.token)
                _authState.value = "success"
            } catch (exception: Exception) {
                _authState.value = "error"
                _errorMessage.value = "No se pudo iniciar sesión."
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = "loading"
            _errorMessage.value = null
            try {
                val response = api.register(AuthRequest(email, password))
                tokenStore.saveToken(response.token)
                _authState.value = "success"
            } catch (exception: Exception) {
                _authState.value = "error"
                _errorMessage.value = "No se pudo crear la cuenta."
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenStore.clearToken()
            _authState.value = "logged_out"
            _errorMessage.value = null
        }
    }
}
