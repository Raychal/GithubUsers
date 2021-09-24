package com.raychal.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raychal.githubusers.data.UserRetrofit
import com.raychal.githubusers.model.GithubUser
import com.raychal.githubusers.utils.state.ResultState
import com.raychal.githubusers.utils.state.TypeView

class FollowViewModel : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    private lateinit var type: TypeView

    val dataFollow: LiveData<ResultState<List<GithubUser>>> = Transformations
        .switchMap(username) {
            when (type){
                TypeView.FOLLOWER -> {
                    UserRetrofit.getFollowers(it)
                }
                TypeView.FOLLOWING -> {
                    UserRetrofit.getFollowing(it)
                }
            }
        }

    fun setFollow (user: String, typeView: TypeView) {
        if (username.value == user) {
            return
        }
        username.value = user
        type = typeView
    }
}