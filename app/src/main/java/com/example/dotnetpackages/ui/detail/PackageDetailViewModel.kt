package com.example.dotnetpackages.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotnetpackages.data.api.NuGetApi
import com.example.dotnetpackages.data.model.PackageDto
import com.example.dotnetpackages.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PackageDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val api: NuGetApi
) : ViewModel() {

    private val packageId: String = checkNotNull(savedStateHandle["packageId"])

    private val _state = MutableStateFlow<UiState<PackageDto>>(UiState.Loading)
    val state: StateFlow<UiState<PackageDto>> = _state.asStateFlow()

    init { loadDetail() }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val response = api.searchPackages(packageId, take = 50)
                val pkg = response.data.firstOrNull { it.id.equals(packageId, ignoreCase = true) }
                if (pkg != null) {
                    _state.value = UiState.Success(pkg)
                } else {
                    _state.value = UiState.Error("Package not found")
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
