package com.raychal.githubusers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raychal.githubusers.data.UserRetrofit
import com.raychal.githubusers.data.local.db.UserDatabase
import com.raychal.githubusers.model.GithubUser

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    val dataFavorite: LiveData<List<GithubUser>>

    init {
        val userDao = UserDatabase.getDatabase(app).userDao()
        dataFavorite = UserRetrofit.getFavorite(userDao)
    }
}