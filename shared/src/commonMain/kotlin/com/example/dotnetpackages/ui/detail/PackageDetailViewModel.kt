package com.example.dotnetpackages.ui.detail

import com.example.dotnetpackages.data.model.PackageDto
import com.example.dotnetpackages.data.repository.PackageRepository
import com.example.dotnetpackages.ui.common.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PackageDetailViewModel(
    private val packageId: String,
    private val repository: PackageRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _state = MutableStateFlow<UiState<PackageDto>>(UiState.Loading)
    val state: StateFlow<UiState<PackageDto>> = _state.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        scope.launch {
            _state.value = UiState.Loading
            try {
                val pkg = repository.getPackageDetail(packageId)
                if (pkg != null) {
                    _state.value = UiState.Success(pkg)
                } else {
                    _state.value = UiState.Error("Package not found")
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
