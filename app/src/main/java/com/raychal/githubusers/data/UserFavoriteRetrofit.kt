package com.raychal.githubusers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raychal.githubusers.data.local.db.UserDao
import com.raychal.githubusers.data.remote.Network
import com.raychal.githubusers.model.GithubUser
import com.raychal.githubusers.utils.state.ResultState
import kotlinx.coroutines.Dispatchers

class UserFavoriteRetrofit(private val userDao: UserDao) {
    private val favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username: String) = liveData(Dispatchers.IO){
        emit(ResultState.loading(null))
        val user = userDao.getUserDetail(username)
        if (user != null){
            favorite.postValue(true)
            try {
                emit(ResultState.success(Network.networkService.getDetailUser(username)))
            } catch (e: Exception){
                emit(ResultState.error(null, e.message ?: "Error"))
            }
        } else {
            favorite.postValue(false)
            try {
                emit(ResultState.success(Network.networkService.getDetailUser(username)))
            } catch (e: Exception){
                emit(ResultState.error(null, e.message ?: "Error"))
            }
        }
    }

    suspend fun insert(user: GithubUser){
        userDao.insertUser(user)
        favorite.value = true
    }

    suspend fun delete(user: GithubUser){
        userDao.deleteUser(user)
        favorite.value = false
    }

    val isFavorite: LiveData<Boolean> = favorite
}