package com.raychal.githubusers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.raychal.githubusers.data.local.UserDataSource
import com.raychal.githubusers.model.GithubUser
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {
    fun getUserList():LiveData<List<GithubUser>> = liveData(Dispatchers.IO) {
        emit(source.getUsers())
    }
}