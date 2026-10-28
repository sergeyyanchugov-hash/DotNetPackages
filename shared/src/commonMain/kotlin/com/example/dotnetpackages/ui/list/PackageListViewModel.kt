package com.example.dotnetpackages.ui.list

import com.example.dotnetpackages.data.model.PackageDto
import com.example.dotnetpackages.data.repository.PackageRepository
import com.example.dotnetpackages.ui.common.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PackageListViewModel(
    private val repository: PackageRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _state = MutableStateFlow<UiState<List<PackageDto>>>(UiState.Loading)
    val state: StateFlow<UiState<List<PackageDto>>> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadPackages()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        loadPackages(query)
    }

    fun clearSearch() {
        onSearchQueryChanged("")
    }

    fun loadPackages(query: String = _searchQuery.value) {
        scope.launch {
            _state.value = UiState.Loading
            try {
                val packages = repository.getPackages(query)
                _state.value = UiState.Success(packages)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
