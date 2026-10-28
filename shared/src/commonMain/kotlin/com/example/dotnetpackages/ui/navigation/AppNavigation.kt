package com.example.dotnetpackages.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.dotnetpackages.data.repository.PackageRepository
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.example.dotnetpackages.ui.detail.PackageDetailScreen
import com.example.dotnetpackages.ui.detail.PackageDetailViewModel
import com.example.dotnetpackages.ui.list.PackageListScreen
import com.example.dotnetpackages.ui.list.PackageListViewModel

@Composable
fun NuGetAppNavigation(repository: PackageRepository) {
    val configuration = remember {
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.List::class)
                    subclass(Route.Detail::class)
                }
            }
        }
    }
    val backStack = rememberNavBackStack(configuration, Route.List)

    val listViewModel = remember(repository) { PackageListViewModel(repository) }

    val provider = entryProvider<NavKey> {
        entry<Route.List> {
            val state by listViewModel.state.collectAsState()
            val query by listViewModel.searchQuery.collectAsState()

            PackageListScreen(
                uiState = state,
                searchQuery = query,
                onSearchQueryChange = { listViewModel.onSearchQueryChanged(it) },
                onClearSearch = { listViewModel.clearSearch() },
                onPackageClick = { packageId ->
                    backStack.add(Route.Detail(packageId))
                }
            )
        }

        entry<Route.Detail> { route ->
            val detailViewModel = remember(route.packageId, repository) {
                PackageDetailViewModel(route.packageId, repository)
            }
            val state by detailViewModel.state.collectAsState()

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
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it / 3 } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it / 3 } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
        },
        entryProvider = provider
    )
}
