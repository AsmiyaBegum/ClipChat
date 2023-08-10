package com.ab.clipchat.api

import com.ab.clipchat.model.GitHubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiInterface {
        @GET("users")
        fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): Call<List<GitHubUser>>
}