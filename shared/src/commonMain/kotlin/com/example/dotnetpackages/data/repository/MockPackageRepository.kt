package com.example.dotnetpackages.data.repository

import com.example.dotnetpackages.data.model.PackageDto
import com.example.dotnetpackages.data.model.PackageVersionDto

class MockPackageRepository : PackageRepository {

    override suspend fun getPackages(query: String): List<PackageDto> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return ALL_PACKAGES
        return ALL_PACKAGES.filter {
            it.id.contains(trimmed, ignoreCase = true) ||
            (it.description?.contains(trimmed, ignoreCase = true) == true)
        }
    }

    override suspend fun getPackageDetail(id: String): PackageDto? {
        return ALL_PACKAGES.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }

    companion object {
        val ALL_PACKAGES = listOf(
            PackageDto(
                id = "Newtonsoft.Json",
                version = "13.0.3",
                description = "Popular high-performance JSON framework for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("13.0.3"), PackageVersionDto("13.0.2"), PackageVersionDto("13.0.1")),
                dependencies = listOf("System.Text.Json", "Microsoft.CSharp")
            ),
            PackageDto(
                id = "Serilog",
                version = "4.0.1",
                description = "Simple .NET logging with fully-structured events",
                iconUrl = null,
                versions = listOf(PackageVersionDto("4.0.1"), PackageVersionDto("4.0.0"), PackageVersionDto("3.1.1")),
                dependencies = listOf("Microsoft.Extensions.Logging")
            ),
            PackageDto(
                id = "AutoMapper",
                version = "13.0.1",
                description = "A convention-based object-object mapper in .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("13.0.1"), PackageVersionDto("12.0.1")),
                dependencies = listOf("Microsoft.Extensions.Options")
            ),
            PackageDto(
                id = "Dapper",
                version = "2.1.35",
                description = "High performance Micro-ORM for ADO.NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("2.1.35"), PackageVersionDto("2.0.123")),
                dependencies = listOf("System.Data.SqlClient")
            ),
            PackageDto(
                id = "Moq",
                version = "4.20.72",
                description = "Popular and friendly mocking framework for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("4.20.72"), PackageVersionDto("4.18.4")),
                dependencies = listOf("Castle.Core")
            ),
            PackageDto(
                id = "NUnit",
                version = "4.2.2",
                description = "Unit testing framework for all .NET languages",
                iconUrl = null,
                versions = listOf(PackageVersionDto("4.2.2"), PackageVersionDto("4.1.0"), PackageVersionDto("3.14.0")),
                dependencies = listOf("NETStandard.Library")
            ),
            PackageDto(
                id = "xunit",
                version = "2.9.0",
                description = "Free, open source, community-focused unit testing tool for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("2.9.0"), PackageVersionDto("2.8.1")),
                dependencies = listOf("xunit.core", "xunit.assert")
            ),
            PackageDto(
                id = "FluentValidation",
                version = "11.10.0",
                description = "A popular .NET validation library for building strongly-typed validation rules",
                iconUrl = null,
                versions = listOf(PackageVersionDto("11.10.0"), PackageVersionDto("11.9.2")),
                dependencies = listOf("System.ComponentModel.Annotations")
            ),
            PackageDto(
                id = "MediatR",
                version = "12.4.1",
                description = "Simple, unambitious mediator implementation in .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("12.4.1"), PackageVersionDto("12.3.0")),
                dependencies = listOf("Microsoft.Extensions.DependencyInjection.Abstractions")
            ),
            PackageDto(
                id = "Polly",
                version = "8.4.2",
                description = "Polly is a .NET resilience and transient-fault-handling library",
                iconUrl = null,
                versions = listOf(PackageVersionDto("8.4.2"), PackageVersionDto("8.3.1")),
                dependencies = listOf("Polly.Core")
            ),
            PackageDto(
                id = "Refit",
                version = "7.2.1",
                description = "The automatic type-safe REST library for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("7.2.1"), PackageVersionDto("7.0.0")),
                dependencies = listOf("System.Text.Json")
            ),
            PackageDto(
                id = "RestSharp",
                version = "112.1.0",
                description = "Simple REST and HTTP API Client for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("112.1.0"), PackageVersionDto("111.4.1")),
                dependencies = listOf("System.Text.Json")
            ),
            PackageDto(
                id = "BenchmarkDotNet",
                version = "0.14.0",
                description = "Powerful .NET library for benchmarking",
                iconUrl = null,
                versions = listOf(PackageVersionDto("0.14.0"), PackageVersionDto("0.13.12")),
                dependencies = listOf("System.Management")
            ),
            PackageDto(
                id = "Microsoft.EntityFrameworkCore",
                version = "8.0.8",
                description = "Entity Framework Core is a modern object-database mapper for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("8.0.8"), PackageVersionDto("8.0.7")),
                dependencies = listOf("Microsoft.EntityFrameworkCore.Abstractions")
            ),
            PackageDto(
                id = "Microsoft.Extensions.Logging",
                version = "8.0.0",
                description = "Logging infrastructure default interfaces for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("8.0.0"), PackageVersionDto("7.0.0")),
                dependencies = listOf("Microsoft.Extensions.DependencyInjection.Abstractions")
            ),
            PackageDto(
                id = "Microsoft.Extensions.DependencyInjection",
                version = "8.0.0",
                description = "Default dependency injection container implementation for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("8.0.0"), PackageVersionDto("7.0.0")),
                dependencies = listOf("Microsoft.Extensions.DependencyInjection.Abstractions")
            ),
            PackageDto(
                id = "System.Text.Json",
                version = "8.0.4",
                description = "Provides high-performance and low-allocating JSON features for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("8.0.4"), PackageVersionDto("8.0.0")),
                dependencies = listOf("System.Runtime.CompilerServices.Unsafe")
            ),
            PackageDto(
                id = "Grpc.Net.Client",
                version = "2.66.0",
                description = "gRPC client library for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("2.66.0"), PackageVersionDto("2.65.0")),
                dependencies = listOf("Grpc.Core.Api")
            ),
            PackageDto(
                id = "Swashbuckle.AspNetCore",
                version = "6.8.1",
                description = "Swagger tools for documenting APIs built on ASP.NET Core",
                iconUrl = null,
                versions = listOf(PackageVersionDto("6.8.1"), PackageVersionDto("6.7.0")),
                dependencies = listOf("Microsoft.AspNetCore.Mvc.ApiExplorer")
            ),
            PackageDto(
                id = "Humanizer",
                version = "2.14.1",
                description = "Humanizer meets all your .NET needs for manipulating and formatting strings, enums, dates, times, timespans, numbers and quantities",
                iconUrl = null,
                versions = listOf(PackageVersionDto("2.14.1"), PackageVersionDto("2.13.0")),
                dependencies = listOf("Humanizer.Core")
            ),
            PackageDto(
                id = "MassTransit",
                version = "8.2.5",
                description = "Distributed Application Framework for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("8.2.5"), PackageVersionDto("8.1.3")),
                dependencies = listOf("MassTransit.Abstractions")
            ),
            PackageDto(
                id = "Quartz",
                version = "3.13.0",
                description = "Quartz.NET is a full-featured, open source job scheduling system for .NET",
                iconUrl = null,
                versions = listOf(PackageVersionDto("3.13.0"), PackageVersionDto("3.12.0")),
                dependencies = listOf("Microsoft.Extensions.Logging.Abstractions")
            )
        )
    }
}
