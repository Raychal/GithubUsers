package com.raychal.githubusers.data

import androidx.lifecycle.liveData
import com.raychal.githubusers.data.local.db.UserDao
import com.raychal.githubusers.data.remote.Network
import com.raychal.githubusers.utils.state.ResultState
import kotlinx.coroutines.Dispatchers

object UserRetrofit {
    fun searchUsers(query: String) = liveData(Dispatchers.IO) {
        emit(ResultState.loading(null))
        try {
            val userSearch = Network.networkService.getSearchUser(query)
            emit(ResultState.success(userSearch.items))
        } catch (exception: Exception) {
            emit(ResultState.error(null, exception.message ?: "Error"))
        }
    }

//    fun getDetailUser(username: String) = liveData(Dispatchers.IO) {
//        emit(ResultState.loading(null))
//        try {
//            emit(ResultState.success(Network.networkService.getDetailUser(username)))
//        } catch (exception: Exception) {
//            emit(ResultState.error(null, exception.message ?: "Error"))
//        }
//    }

    fun getFollowers(username: String) = liveData(Dispatchers.IO) {
        emit(ResultState.loading(null))
        try {
            emit(ResultState.success(Network.networkService.getFollowerUser(username)))
        } catch (exception: Exception) {
            emit(ResultState.error(null, exception.message ?: "Error"))
        }
    }

    fun getFollowing(username: String) = liveData(Dispatchers.IO) {
        emit(ResultState.loading(null))
        try {
            emit(ResultState.success(Network.networkService.getFollowingUser(username)))
        } catch (exception: Exception) {
            emit(ResultState.error(null, exception.message ?: "Error"))
        }
    }

    fun getFavorite(userDao: UserDao) = userDao.getUserList()
}