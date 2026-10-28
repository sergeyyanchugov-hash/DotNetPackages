package com.example.dotnetpackages

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.dotnetpackages.data.repository.MockPackageRepository
import com.example.dotnetpackages.ui.navigation.NuGetAppNavigation
import com.example.dotnetpackages.ui.theme.NugetCatalogTheme

fun main() = application {
    val repository = MockPackageRepository()
    Window(
        onCloseRequest = ::exitApplication,
        title = "DotNet Packages"
    ) {
        NugetCatalogTheme {
            NuGetAppNavigation(repository = repository)
        }
    }
}
