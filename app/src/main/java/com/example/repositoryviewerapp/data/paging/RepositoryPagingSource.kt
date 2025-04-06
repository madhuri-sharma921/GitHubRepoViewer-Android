package com.example.repositoryviewerapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.repositoryviewerapp.data.api.GithubApi
import com.example.repositoryviewerapp.data.model.Repository
import retrofit2.HttpException
import java.io.IOException

class RepositoryPagingSource(
        private val api: GithubApi,
        private val username: String
    ) : PagingSource<Int, Repository>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
            val page = params.key ?: 1

            return try {
                val repositories = api.getRepositories(
                    username = username,
                    page = page,
                    perPage = 10
                )

                LoadResult.Page(
                    data = repositories,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (repositories.isEmpty()) null else page + 1
                )
            } catch (e: IOException) {
                LoadResult.Error(e)
            } catch (e: HttpException) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }
    }