package com.example.dotnetpackages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dotnetpackages.data.repository.MockPackageRepository
import com.example.dotnetpackages.ui.navigation.NuGetAppNavigation
import com.example.dotnetpackages.ui.theme.NugetCatalogTheme

class MainActivity : ComponentActivity() {

    private val repository = MockPackageRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NugetCatalogTheme {
                NuGetAppNavigation(repository = repository)
            }
        }
    }
}
