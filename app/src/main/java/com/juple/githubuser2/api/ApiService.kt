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

    @Headers("Authorization: $TOKEN")
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @Headers("Authorization: $TOKEN")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: $TOKEN")
    @GET("users/{username}/followers")
    fun getDetailFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @Headers("Authorization: $TOKEN")
    @GET("users/{username}/following")
    fun getDetailFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    companion object {
        private const val TOKEN = "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"
    }
}