package com.example.aipetcareassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipetcareassistant.data.Analysis
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AnalysisViewModel(private val context: Context) : ViewModel() {
    private val api = ApiClient.create(context)

    private val _analyses = MutableStateFlow<List<Analysis>>(emptyList())
    val analyses: StateFlow<List<Analysis>> = _analyses

    private val _latestAnalysis = MutableStateFlow<Analysis?>(null)
    val latestAnalysis: StateFlow<Analysis?> = _latestAnalysis

    fun loadAnalyses(petId: String) {
        viewModelScope.launch {
            _analyses.value = api.listAnalyses(petId)
        }
    }

    fun uploadPhoto(petId: String, part: MultipartBody.Part) {
        viewModelScope.launch {
            _latestAnalysis.value = api.uploadPhoto(petId, part)
        }
    }
}
