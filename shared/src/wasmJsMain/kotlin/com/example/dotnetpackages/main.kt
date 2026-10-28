package com.example.dotnetpackages

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.dotnetpackages.data.repository.MockPackageRepository
import com.example.dotnetpackages.ui.navigation.NuGetAppNavigation
import com.example.dotnetpackages.ui.theme.NugetCatalogTheme
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val repository = MockPackageRepository()
    document.body?.let {
        ComposeViewport(it) {
            NugetCatalogTheme {
                NuGetAppNavigation(repository = repository)
            }
        }
    }
}
