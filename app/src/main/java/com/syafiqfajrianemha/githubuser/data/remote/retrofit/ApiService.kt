package com.syafiqfajrianemha.githubuser.data.remote.retrofit

import com.syafiqfajrianemha.githubuser.data.remote.response.DetailUserResponse
import com.syafiqfajrianemha.githubuser.data.remote.response.GithubResponse
import com.syafiqfajrianemha.githubuser.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUser(
        @Query("q") keyword: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun listFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun listFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}