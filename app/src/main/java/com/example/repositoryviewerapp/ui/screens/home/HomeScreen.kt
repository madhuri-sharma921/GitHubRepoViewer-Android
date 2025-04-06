package com.example.repositoryviewerapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.repositoryviewerapp.ui.components.RepositoryList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onRepositoryClick: (Long) -> Unit
) {
    val repositories = viewModel.repositoriesFlow.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GitHub Repositories") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { viewModel.updateUsername(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    label = { Text("GitHub Username") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.searchRepositories(viewModel.username)
                            focusManager.clearFocus()
                        }
                    )
                )

                Button(
                    onClick = {
                        viewModel.searchRepositories(viewModel.username)
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            }

            RepositoryList(
                repositories = repositories,
                onRepositoryClick = onRepositoryClick
            )
        }
    }
}