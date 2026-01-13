package com.example.aipetcareassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aipetcareassistant.R
import com.example.aipetcareassistant.data.Analysis

@Composable
fun AnalysisResultScreen(result: Analysis?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Análisis",
            style = MaterialTheme.typography.headlineSmall
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Texto del análisis",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = result?.summaryText
                        ?: "Aún no hay un análisis disponible. Sube una foto para generar uno.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Text(
            text = if (result == null) "Respuesta de la IA: Pendiente" else "Respuesta de la IA: Generada",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "Condición corporal: ${result?.bodyCondition ?: "pendiente"}")
        Text(text = "Condición del pelaje: ${result?.coatCondition ?: "pendiente"}")
        Text(
            text = "Recomendaciones: ${result?.recommendations ?: "completa un análisis para ver sugerencias."}"
        )
        Text(text = stringResource(id = R.string.disclaimer_short))
    }
}
