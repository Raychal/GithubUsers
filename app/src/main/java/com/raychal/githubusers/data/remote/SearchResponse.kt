package com.raychal.githubusers.data.remote

import com.raychal.githubusers.model.GithubUser

data class SearchResponse(
    val total_count : Int,
    val incomplete_results: Boolean? = null,
    val items : List<GithubUser>
)