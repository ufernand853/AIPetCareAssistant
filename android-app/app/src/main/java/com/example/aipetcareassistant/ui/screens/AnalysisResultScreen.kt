package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.R

@Composable
fun AnalysisResultScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Analysis Results")
        Text(text = "Body condition: ideal")
        Text(text = "Coat condition: normal")
        Text(text = "Summary: Placeholder summary of analysis results.")
        Text(text = "Recommendations: Balanced diet and regular vet checks.")
        Text(text = stringResource(id = R.string.disclaimer_short))
    }
}
