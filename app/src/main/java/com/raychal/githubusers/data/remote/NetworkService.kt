package com.raychal.githubusers.data.remote

import com.raychal.githubusers.model.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("search/users?")
    suspend fun getSearchUser(
        @Query("q") q : String?
    ) : SearchResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String?
    ) : GithubUser

    @GET("users/{username}/followers")
    suspend fun getFollowerUser(
        @Path("username") username: String?
    ) : List<GithubUser>

    @GET("users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username: String?
    ) : List<GithubUser>
}