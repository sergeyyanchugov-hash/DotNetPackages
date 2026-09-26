package com.example.dotnetpackages.data.api

import com.example.dotnetpackages.data.model.NuGetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NuGetApi {
    @GET("query")
    suspend fun searchPackages(
        @Query("q") query: String,
        @Query("take") take: Int = 20
    ): NuGetResponse
}
