package com.example.aipetcareassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aipetcareassistant.data.Analysis
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
    var analysisResult by remember { mutableStateOf<Analysis?>(null) }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(onContinue = { navController.navigate("login") }) }
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("pets") },
                onRegister = { navController.navigate("register") }
            )
        }
        composable("register") { RegisterScreen(onRegistered = { navController.navigate("pets") }) }
        composable("pets") {
            PetListScreen(
                onPetSelected = { petId -> navController.navigate("petDetail/$petId") }
            )
        }
        composable("petDetail/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId").orEmpty()
            PetDetailScreen(
                onAnalyze = { navController.navigate("analysis/$petId") },
                onReminders = { navController.navigate("reminders") },
                onChat = { navController.navigate("chat") }
            )
        }
        composable("analysis/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId").orEmpty()
            PhotoAnalysisScreen(
                petId = petId,
                onDone = { analysis ->
                    analysisResult = analysis
                    navController.navigate("analysisResult")
                }
            )
        }
        composable("analysisResult") { AnalysisResultScreen(result = analysisResult) }
        composable("reminders") { RemindersScreen() }
        composable("chat") { ChatScreen() }
        composable("settings") {
            SettingsScreen(
                onViewApiLog = { navController.navigate("apiLog") },
                onLogout = { navController.navigate("login") }
            )
        }
        composable("apiLog") { ApiLogScreen(onBack = { navController.popBackStack() }) }
    }
}
