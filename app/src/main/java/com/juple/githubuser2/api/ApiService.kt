package com.juple.githubuser2.api

import com.juple.githubuser2.data.DetailUserResponse
import com.juple.githubuser2.data.User
import com.juple.githubuser2.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token $TOKEN")
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}/followers")
    fun getDetailFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}/following")
    fun getDetailFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    companion object {
        private const val TOKEN = "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"
    }
}