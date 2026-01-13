package com.example.aipetcareassistant.data

data class AuthRequest(val email: String, val password: String)

data class AuthResponse(val token: String, val user: User)

data class User(val id: String, val email: String)

data class Pet(
    val _id: String,
    val name: String,
    val species: String,
    val breed: String,
    val ageYears: Double,
    val weightKg: Double,
    val gender: String,
    val notes: String? = null,
    val photoUrl: String? = null
)

data class PetRequest(
    val name: String,
    val species: String,
    val breed: String,
    val ageYears: Double,
    val weightKg: Double,
    val gender: String,
    val notes: String? = null
)

data class Analysis(
    val _id: String,
    val imageUrl: String,
    val bodyCondition: String,
    val coatCondition: String,
    val summaryText: String,
    val recommendations: String
)

data class Reminder(
    val _id: String,
    val type: String,
    val title: String,
    val description: String?,
    val dueDate: String,
    val isCompleted: Boolean
)

data class ReminderRequest(
    val type: String,
    val title: String,
    val description: String?,
    val dueDate: String
)

data class ChatRequest(val petId: String?, val message: String)

data class ChatResponse(val reply: String, val disclaimer: String)

data class ProductRecommendation(
    val _id: String,
    val species: String,
    val targetCondition: String,
    val title: String,
    val brand: String,
    val description: String,
    val affiliateUrl: String,
    val imageUrl: String
)
