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
    onRepositoryClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
    
) {
    val repositories = viewModel.repositoriesFlow.collectAsLazyPagingItems()
   
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
            RepositoryList(
                repositories = repositories,
                onRepositoryClick = onRepositoryClick
            )
        }
    }  
}
