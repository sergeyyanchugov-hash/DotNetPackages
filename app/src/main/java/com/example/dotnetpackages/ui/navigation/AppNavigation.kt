package com.example.dotnetpackages.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dotnetpackages.data.api.NuGetApi
import com.example.dotnetpackages.ui.detail.PackageDetailScreen
import com.example.dotnetpackages.ui.detail.PackageDetailViewModel
import com.example.dotnetpackages.ui.list.PackageListScreen
import com.example.dotnetpackages.ui.list.PackageListViewModel

@Composable
fun NuGetAppNavigation(api: NuGetApi) {
    val backStack = rememberSaveable { mutableStateListOf<Route>(Route.List) }

    val provider = entryProvider {
        entry<Route.List> {
            val viewModel: PackageListViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return PackageListViewModel(api) as T
                    }
                }
            )
            val state by viewModel.state.collectAsState()
            PackageListScreen(
                uiState = state,
                onPackageClick = { packageId ->
                    backStack.add(Route.Detail(packageId))
                }
            )
        }

        entry<Route.Detail> { route ->
            val viewModel: PackageDetailViewModel = viewModel(
                key = route.packageId,
                factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return PackageDetailViewModel(
                            SavedStateHandle(mapOf("packageId" to route.packageId)),
                            api
                        ) as T
                    }
                }
            )
            val state by viewModel.state.collectAsState()
            PackageDetailScreen(
                uiState = state,
                onBackClick = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.lastIndex)
                    }
                },
                onHomeClick = {
                    while (backStack.size > 1) {
                        backStack.removeAt(backStack.lastIndex)
                    }
                },
                onDependencyClick = { dependencyId ->
                    backStack.add(Route.Detail(dependencyId))
                }
            )
        }
    }

    NavDisplay(
        backStack = backStack,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it / 3 } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it / 3 } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
        },
        entryProvider = provider
    )
}
