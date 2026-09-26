package com.example.dotnetpackages.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NuGetResponse(val data: List<PackageDto>)

@Serializable
data class PackageDto(
    val id: String,
    val version: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val versions: List<PackageVersionDto> = emptyList(),
    val dependencies: List<String> = listOf("Newtonsoft.Json", "System.Text.Json")
)

@Serializable
data class PackageVersionDto(val version: String)
