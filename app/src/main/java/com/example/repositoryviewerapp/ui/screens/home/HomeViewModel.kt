package com.example.repositoryviewerapp.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.repositoryviewerapp.data.model.Repository
import com.example.repositoryviewerapp.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    var username by mutableStateOf("google")
        private set

    var repositoriesFlow: Flow<PagingData<Repository>> = emptyFlow()
        private set

    init {
        searchRepositories(username)
    }

    fun updateUsername(newUsername: String) {
        username = newUsername
    }

    fun searchRepositories(username: String) {
        viewModelScope.launch {
            repositoriesFlow = repository.getRepositories(username)
                .cachedIn(viewModelScope)
        }
    }
}