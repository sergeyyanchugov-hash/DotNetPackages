package com.example.dotnetpackages.data.repository

import com.example.dotnetpackages.data.model.PackageDto

interface PackageRepository {
    suspend fun getPackages(query: String = ""): List<PackageDto>
    suspend fun getPackageDetail(id: String): PackageDto?
}
