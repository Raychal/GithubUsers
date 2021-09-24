package com.raychal.githubusers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raychal.githubusers.data.UserRepository
import com.raychal.githubusers.data.local.UserDataSource

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val source = UserDataSource(application.contentResolver)
        repository = UserRepository(source)
    }

    var userList = repository.getUserList()
}