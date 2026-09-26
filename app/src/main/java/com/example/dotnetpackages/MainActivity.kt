package com.example.dotnetpackages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dotnetpackages.data.api.MockNuGetApi
import com.example.dotnetpackages.data.api.NuGetApi
import com.example.dotnetpackages.ui.navigation.NuGetAppNavigation
import com.example.dotnetpackages.ui.theme.NugetCatalogTheme

class MainActivity : ComponentActivity() {

    private val api: NuGetApi by lazy { MockNuGetApi() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NugetCatalogTheme {
                NuGetAppNavigation(api = api)
            }
        }
    }
}
