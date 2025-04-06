package com.example.repositoryviewerapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.repositoryviewerapp.data.api.GithubApi
import com.example.repositoryviewerapp.data.model.Repository
import com.example.repositoryviewerapp.data.paging.RepositoryPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : GithubRepository {

    override fun getRepositories(username: String): Flow<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepositoryPagingSource(api, username) }
        ).flow
    }
}