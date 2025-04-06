package com.example.repositoryviewerapp.data.repository

import androidx.paging.PagingData
import com.example.repositoryviewerapp.data.model.Repository
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getRepositories(username: String): Flow<PagingData<Repository>>
}