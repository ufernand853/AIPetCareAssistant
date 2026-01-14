package com.example.aipetcareassistant.network

import android.content.Context
import com.example.aipetcareassistant.data.ApiCallLogEntry
import com.example.aipetcareassistant.data.ApiCallLogStore
import com.example.aipetcareassistant.data.TokenStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    const val BASE_URL = "http://10.0.2.2:4000/api/"
    val baseHost: String
        get() = BASE_URL.toHttpUrlOrNull()?.host ?: BASE_URL

    fun create(context: Context): ApiService {
        val tokenStore = TokenStore(context)
        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { tokenStore.tokenFlow.firstOrNull() }
            val request = if (token != null) {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                chain.request()
            }
            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val apiLogInterceptor = Interceptor { chain ->
            val request = chain.request()
            val startTime = System.currentTimeMillis()
            try {
                val response = chain.proceed(request)
                val duration = System.currentTimeMillis() - startTime
                ApiCallLogStore.add(
                    ApiCallLogEntry(
                        method = request.method,
                        url = request.url.toString(),
                        statusCode = response.code,
                        durationMs = duration,
                        timestampMillis = startTime
                    )
                )
                response
            } catch (exception: Exception) {
                val duration = System.currentTimeMillis() - startTime
                ApiCallLogStore.add(
                    ApiCallLogEntry(
                        method = request.method,
                        url = request.url.toString(),
                        statusCode = -1,
                        durationMs = duration,
                        timestampMillis = startTime
                    )
                )
                throw exception
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(apiLogInterceptor)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
