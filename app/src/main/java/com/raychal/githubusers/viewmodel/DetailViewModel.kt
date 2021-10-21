package com.raychal.githubusers.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.raychal.githubusers.data.UserFavoriteRetrofit
import com.raychal.githubusers.data.local.db.UserDao
import com.raychal.githubusers.data.local.db.UserDatabase
import com.raychal.githubusers.model.GithubUser
import com.raychal.githubusers.utils.state.ResultState
import kotlinx.coroutines.launch

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private val userDao: UserDao = UserDatabase.getDatabase(app).userDao()
    private val userFavoriteRetrofit: UserFavoriteRetrofit = UserFavoriteRetrofit(userDao)

    fun data(username: String): LiveData<ResultState<GithubUser>> = userFavoriteRetrofit.getDetailUser(username)

    fun addFavorite(githubUser: GithubUser) = viewModelScope.launch {
        userFavoriteRetrofit.insert(githubUser)
    }

    fun removeFavorite(githubUser: GithubUser) = viewModelScope.launch {
        userFavoriteRetrofit.delete(githubUser)
    }

    val isFavorite: LiveData<Boolean> = userFavoriteRetrofit.isFavorite

}