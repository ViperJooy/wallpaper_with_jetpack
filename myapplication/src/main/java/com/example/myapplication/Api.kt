package com.example.myapplication

import retrofit2.http.GET

interface Api {
    @GET("api/v2/article/articlelist")
    suspend fun listRepos(): Article
}