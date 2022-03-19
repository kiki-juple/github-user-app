package com.juple.githubuser2.data

data class DetailUserResponse(
    val login: String,
    val name: String,
    val avatar_url: String,
    val bio: String,
    val blog: String,
    val company: String,
    val location: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val following_url: String,
    val followers_url: String
)
