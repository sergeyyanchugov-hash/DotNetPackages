package com.example.dotnetpackages.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object List : Route

    @Serializable
    data class Detail(val packageId: String) : Route
}
