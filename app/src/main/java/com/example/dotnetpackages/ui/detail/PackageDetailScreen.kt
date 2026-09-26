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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dotnetpackages.R
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
                title = { Text(stringResource(R.string.nav_title_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.btn_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.btn_home)
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator(Modifier.padding(padding))
            is UiState.Error -> Column(Modifier.padding(padding).padding(16.dp)) {
                Text(stringResource(R.string.error_prefix, uiState.message))
                Spacer(Modifier.height(16.dp))
                Button(onClick = onHomeClick) {
                    Text(stringResource(R.string.btn_home))
                }
            }
            is UiState.Success<*> -> {
                val pkg = uiState.data as PackageDto
                LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    item {
                        Row {
                            AsyncImage(model = pkg.iconUrl, contentDescription = null, modifier = Modifier.size(72.dp))
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(text = pkg.id, style = MaterialTheme.typography.headlineMedium)
                                Text(text = pkg.version, style = MaterialTheme.typography.titleMedium)
                            }
                        }
                        HorizontalDivider(Modifier.padding(vertical = 16.dp))
                    }

                    item { Text(stringResource(R.string.label_available_versions), style = MaterialTheme.typography.titleLarge) }
                    items(pkg.versions) { version ->
                        Text(version.version, modifier = Modifier.padding(vertical = 4.dp))
                    }

                    item { HorizontalDivider(Modifier.padding(vertical = 16.dp)) }

                    item { Text(stringResource(R.string.label_dependencies), style = MaterialTheme.typography.titleLarge) }
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
                            Text(stringResource(R.string.btn_return_main))
                        }
                    }
                }
            }
        }
    }
}
