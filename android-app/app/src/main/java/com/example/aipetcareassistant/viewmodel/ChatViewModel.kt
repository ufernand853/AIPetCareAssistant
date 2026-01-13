package com.example.aipetcareassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipetcareassistant.data.ChatRequest
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val context: Context) : ViewModel() {
    private val api = ApiClient.create(context)

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    fun sendMessage(petId: String?, message: String) {
        viewModelScope.launch {
            val response = api.chat(ChatRequest(petId, message))
            _messages.value = _messages.value + listOf("You: $message", "AI: ${response.reply}")
        }
    }
}
