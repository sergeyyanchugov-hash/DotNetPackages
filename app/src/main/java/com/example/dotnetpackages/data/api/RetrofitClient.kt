package com.example.dotnetpackages.data.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api-v2v3search-0.nuget.org/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun create(): NuGetApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(NuGetApi::class.java)
    }
}
