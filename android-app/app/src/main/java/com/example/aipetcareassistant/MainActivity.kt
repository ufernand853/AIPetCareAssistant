package com.example.aipetcareassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aipetcareassistant.ui.screens.*
import com.example.aipetcareassistant.ui.theme.AIPetCareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIPetCareTheme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(onContinue = { navController.navigate("login") }) }
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("pets") },
                onRegister = { navController.navigate("register") }
            )
        }
        composable("register") { RegisterScreen(onRegistered = { navController.navigate("pets") }) }
        composable("pets") { PetListScreen(onPetSelected = { navController.navigate("petDetail") }) }
        composable("petDetail") {
            PetDetailScreen(
                onAnalyze = { navController.navigate("analysis") },
                onReminders = { navController.navigate("reminders") },
                onChat = { navController.navigate("chat") }
            )
        }
        composable("analysis") { PhotoAnalysisScreen(onDone = { navController.navigate("analysisResult") }) }
        composable("analysisResult") { AnalysisResultScreen(onShare = {}) }
        composable("reminders") { RemindersScreen() }
        composable("chat") { ChatScreen() }
        composable("settings") { SettingsScreen(onLogout = { navController.navigate("login") }) }
    }
}
