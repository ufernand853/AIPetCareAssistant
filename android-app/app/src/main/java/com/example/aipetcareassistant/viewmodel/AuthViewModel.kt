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

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = "loading"
            val response = api.login(AuthRequest(email, password))
            tokenStore.saveToken(response.token)
            _authState.value = "success"
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = "loading"
            val response = api.register(AuthRequest(email, password))
            tokenStore.saveToken(response.token)
            _authState.value = "success"
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenStore.clearToken()
            _authState.value = "logged_out"
        }
    }
}
