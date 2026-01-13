package com.example.aipetcareassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipetcareassistant.data.Reminder
import com.example.aipetcareassistant.data.ReminderRequest
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RemindersViewModel(private val context: Context) : ViewModel() {
    private val api = ApiClient.create(context)

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders

    fun loadReminders(petId: String) {
        viewModelScope.launch {
            _reminders.value = api.listReminders(petId)
        }
    }

    fun createReminder(petId: String, request: ReminderRequest) {
        viewModelScope.launch {
            api.createReminder(petId, request)
            loadReminders(petId)
        }
    }
}
