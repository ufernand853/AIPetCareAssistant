package com.example.aipetcareassistant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF009688),
    secondary = Color(0xFFFFB74D)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF009688),
    secondary = Color(0xFFFFB74D)
)

@Composable
fun AIPetCareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
