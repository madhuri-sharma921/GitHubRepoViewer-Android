package com.example.repositoryviewerapp.data.api

import com.example.repositoryviewerapp.data.model.Repository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Repository>
}