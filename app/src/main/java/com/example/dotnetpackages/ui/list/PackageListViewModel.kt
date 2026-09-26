package com.example.dotnetpackages.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotnetpackages.data.api.NuGetApi
import com.example.dotnetpackages.data.model.PackageDto
import com.example.dotnetpackages.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PackageListViewModel(private val api: NuGetApi) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<PackageDto>>>(UiState.Loading)
    val state: StateFlow<UiState<List<PackageDto>>> = _state.asStateFlow()

    init { loadPackages() }

    fun loadPackages(query: String = "") {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val response = api.searchPackages(query, take = 50)
                _state.value = UiState.Success(response.data)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
