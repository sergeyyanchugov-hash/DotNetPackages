package com.example.dotnetpackages.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dotnetpackages.data.model.PackageDto
import com.example.dotnetpackages.ui.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailScreen(
    uiState: UiState<PackageDto>,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDependencyClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали пакета") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "На главную"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator(Modifier.padding(padding))
            is UiState.Error -> Column(Modifier.padding(padding).padding(16.dp)) {
                Text("Ошибка: ${uiState.message}")
                Spacer(Modifier.height(16.dp))
                Button(onClick = onHomeClick) {
                    Text("На главную")
                }
            }
            is UiState.Success -> {
                val pkg = uiState.data
                LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    item {
                        Column {
                            Text(text = pkg.id, style = MaterialTheme.typography.headlineMedium)
                            Text(text = pkg.version, style = MaterialTheme.typography.titleMedium)
                            if (!pkg.description.isNullOrEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                Text(text = pkg.description, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        HorizontalDivider(Modifier.padding(vertical = 16.dp))
                    }

                    item { Text("Доступные версии", style = MaterialTheme.typography.titleLarge) }
                    items(pkg.versions) { version ->
                        Text(version.version, modifier = Modifier.padding(vertical = 4.dp))
                    }

                    item { HorizontalDivider(Modifier.padding(vertical = 16.dp)) }

                    item { Text("Зависимости", style = MaterialTheme.typography.titleLarge) }
                    items(pkg.dependencies) { depId ->
                        TextButton(onClick = { onDependencyClick(depId) }) {
                            Text(depId)
                        }
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = onHomeClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Вернуться на главный экран")
                        }
                    }
                }
            }
        }
    }
}
