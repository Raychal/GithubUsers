package com.raychal.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raychal.githubusers.data.UserRetrofit
import com.raychal.githubusers.model.GithubUser
import com.raychal.githubusers.utils.state.ResultState

class HomeViewModel : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    val searchResult: LiveData<ResultState<List<GithubUser>>> = Transformations
        .switchMap(username) {
            UserRetrofit.searchUsers(it)
        }

    fun setSearch(query: String) {
        if (username.value == query){
            return
        }
        username.value = query
    }
}