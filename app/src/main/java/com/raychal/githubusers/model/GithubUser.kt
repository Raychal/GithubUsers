package com.raychal.githubusers.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
data class GithubUser (
    @PrimaryKey(autoGenerate = false)
    @field:Json(name = "id")
    val id: Int,

    @ColumnInfo(name = "login")
    @field:Json(name = "login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    @field:Json(name = "avatar_url")
    val avatar_url: String,

    @ColumnInfo(name = "name")
    @field:Json(name = "name")
    val name: String?,

    @ColumnInfo(name = "bio")
    @field:Json(name = "bio")
    val bio: String?,

    @ColumnInfo(name = "company")
    @field:Json(name = "company")
    val company: String?,

    @ColumnInfo(name = "location")
    @field:Json(name = "location")
    val location: String?,

    @ColumnInfo(name = "blog")
    @field:Json(name = "blog")
    val blog: String?,

    @ColumnInfo(name = "type")
    @field:Json(name = "type")
    val type: String?,

    @ColumnInfo(name = "public_repos")
    @field:Json(name = "public_repos")
    val publicRepos: Int,

    @ColumnInfo(name = "followers")
    @field:Json(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    @field:Json(name = "following")
    val following: Int
)