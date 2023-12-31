package com.ab.clipchat.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider{
    fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClientProvider.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
            .build()
    }
}

object OkHttpClientProvider {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}


object RetrofitWrapper{
    private const val BASE_URL = "https://run.mocky.io/"
    private const val GITHUB_BASE_URL = "https://api.github.com/"

    val clipVideoApiInterface: ClipVideoAPIInterface by lazy {
        RetrofitProvider.createRetrofit(BASE_URL).create(ClipVideoAPIInterface::class.java)
    }

    val gitHubApiInterface: GitHubApiInterface by lazy {
        RetrofitProvider.createRetrofit(GITHUB_BASE_URL).create(GitHubApiInterface::class.java)
    }
}