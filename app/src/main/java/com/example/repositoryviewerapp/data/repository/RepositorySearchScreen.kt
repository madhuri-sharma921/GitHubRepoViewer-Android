package com.example.repositoryviewerapp.data.repository

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.repositoryviewerapp.R
import com.example.repositoryviewerapp.data.model.Repository
import com.example.repositoryviewerapp.ui.screens.home.HomeViewModel

@Composable
fun RepositorySearchScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val repositories = viewModel.repositoriesFlow.collectAsLazyPagingItems()

    var searchQuery by remember { mutableStateOf(viewModel.username) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("GitHub username") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.isNotEmpty()) {
                            viewModel.updateUsername(searchQuery)
                            viewModel.searchRepositories(searchQuery)
                        }
                    }
                ),
                enabled = repositories.loadState.refresh !is LoadState.Loading
            )

            Button(
                onClick = {
                    if (searchQuery.isNotEmpty()) {
                        viewModel.updateUsername(searchQuery)
                        viewModel.searchRepositories(searchQuery)
                    }
                },
                enabled = repositories.loadState.refresh !is LoadState.Loading && searchQuery.isNotEmpty()
            ) {
                Text("Search")
            }
        }

      
        if (repositories.loadState.refresh is LoadState.Error) {
            Text(
                text = "Error: ${(repositories.loadState.refresh as? LoadState.Error)?.error?.message ?: "Unknown error"}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

     
        if (repositories.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = repositories.itemCount) { index ->
                    repositories[index]?.let { repo ->
                        RepositoryItem(repository = repo)
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }

            
                item {
                    if (repositories.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepositoryItem(repository: Repository) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
          
            Text(
                text = repository.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Owner (if search mode)
            Text(
                text = "by ${repository.owner.login}",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description
            Text(
                text = repository.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = repository.language ?: "Unknown",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Stars
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Stars",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Text(
                        text = formatCount(repository.stars)+ " Stars",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Forks
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fork),
                        contentDescription = "Forks",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = formatCount(repository.forks)+" Forks",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

private fun formatCount(count: Int): String {
    return when {
        count >= 1000000 -> String.format("%.1fM", count / 1000000.0)
        count >= 1000 -> String.format("%.1fK", count / 1000.0)
        else -> count.toString()
    }
}