package com.example.aipetcareassistant.network

import com.example.aipetcareassistant.data.*
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @GET("auth/me")
    suspend fun me(): User

    @GET("pets")
    suspend fun listPets(): List<Pet>

    @POST("pets")
    suspend fun createPet(@Body request: PetRequest): Pet

    @GET("pets/{id}")
    suspend fun getPet(@Path("id") id: String): Pet

    @PUT("pets/{id}")
    suspend fun updatePet(@Path("id") id: String, @Body request: PetRequest): Pet

    @DELETE("pets/{id}")
    suspend fun deletePet(@Path("id") id: String)

    @Multipart
    @POST("pets/{id}/photo")
    suspend fun uploadPhoto(@Path("id") id: String, @Part image: MultipartBody.Part): Analysis

    @GET("pets/{id}/analyses")
    suspend fun listAnalyses(@Path("id") id: String): List<Analysis>

    @GET("pets/analyses/{analysisId}")
    suspend fun getAnalysis(@Path("analysisId") analysisId: String): Analysis

    @GET("pets/{id}/reminders")
    suspend fun listReminders(@Path("id") id: String): List<Reminder>

    @POST("pets/{id}/reminders")
    suspend fun createReminder(@Path("id") id: String, @Body request: ReminderRequest): Reminder

    @PUT("reminders/{id}")
    suspend fun updateReminder(@Path("id") id: String, @Body request: ReminderRequest): Reminder

    @DELETE("reminders/{id}")
    suspend fun deleteReminder(@Path("id") id: String)

    @GET("recommendations")
    suspend fun listRecommendations(
        @Query("species") species: String?,
        @Query("targetCondition") targetCondition: String?
    ): List<ProductRecommendation>

    @POST("chat")
    suspend fun chat(@Body request: ChatRequest): ChatResponse
}
