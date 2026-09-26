package com.example.dotnetpackages.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun PackageListScreen(
    uiState: UiState<List<PackageDto>>,
    onPackageClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nav_title_list)) }
            )
        }
    ) { padding ->
        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator(Modifier.padding(padding))
            is UiState.Error -> Text(
                text = stringResource(R.string.error_prefix, uiState.message),
                modifier = Modifier.padding(padding)
            )
            is UiState.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                val packages = uiState.data as List<PackageDto>
                LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize()) {
                    items(packages, key = { it.id }) { pkg ->
                        PackageListItem(pkg, onClick = { onPackageClick(pkg.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun PackageListItem(pkg: PackageDto, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(pkg.id) },
        supportingContent = { Text(pkg.description ?: "", maxLines = 2) },
        leadingContent = {
            AsyncImage(
                model = pkg.iconUrl,
                contentDescription = "Icon",
                modifier = Modifier.size(48.dp)
            )
        }
    )
}
