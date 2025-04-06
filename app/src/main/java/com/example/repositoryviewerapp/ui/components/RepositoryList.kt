package com.example.repositoryviewerapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.repositoryviewerapp.data.model.Repository


@Composable
    fun RepositoryList(
        repositories: LazyPagingItems<Repository>,
        onRepositoryClick: (Long) -> Unit
    ) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (repositories.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (repositories.loadState.refresh is LoadState.Error) {
            ErrorView(
                message = "Could not load repositories. Please try again.",
                onRetryClick = { repositories.refresh() },
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(repositories.itemCount) { index ->
                    repositories[index]?.let { repository ->
                        RepositoryItem(
                            repository = repository,
                            onClick = { onRepositoryClick(repository.id) }
                        )
                    }
                }

                item {
                    if (repositories.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else if (repositories.loadState.append is LoadState.Error) {
                        ErrorView(
                            message = "Error loading more repositories",
                            onRetryClick = { repositories.retry() }
                        )
                    }
                }
            }
        }
    }

}