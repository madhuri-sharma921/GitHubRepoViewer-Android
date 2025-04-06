package com.example.repositoryviewerapp.data.model

import com.google.gson.annotations.SerializedName

data class Repository (
    val id: Long,
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forks: Int,
    val language: String?,
    @SerializedName("html_url")
    val url: String,
    val owner: Owner
)
data class Owner(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)