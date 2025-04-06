package com.example.repositoryviewerapp.di

import com.example.repositoryviewerapp.data.api.GithubApi
import com.example.repositoryviewerapp.data.repository.GithubRepository
import com.example.repositoryviewerapp.data.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    }

    @Provides
    @Singleton
    fun provideRepositoryApi(okHttpClient: OkHttpClient):GithubApi{
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)

    }
    @Provides
    @Singleton
    fun provideGithubRepository(api:GithubApi):GithubRepository{
        return GithubRepositoryImpl(api)

    }


}