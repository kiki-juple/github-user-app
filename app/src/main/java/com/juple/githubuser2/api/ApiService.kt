package com.juple.githubuser2.api

import com.juple.githubuser2.data.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @Headers("Authorization: token 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")
    @GET("search/users?q={username}")
    fun searchUsers(
        @Path("username") username: String
    ): Call<SearchUser>

    @FormUrlEncoded
    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailUser>

    @FormUrlEncoded
    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getDetailFollowers(
        @Path("username") username: String
    ): Call<FollowerUser>

    @FormUrlEncoded
    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getDetailFollowing(
        @Path("username") username: String
    ): Call<FollowingUser>
}