package com.raychal.githubusers.data.remote

import android.os.Parcelable
import com.raychal.githubusers.model.GithubUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
    val total_count : Int,
    val incomplete_results: Boolean? = null,
    val items : List<GithubUser>
) : Parcelable