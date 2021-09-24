package com.raychal.githubusers.utils.state

import android.content.res.Resources
import android.view.View
import com.raychal.githubusers.R
import com.raychal.githubusers.databinding.FragmentFollowBinding
import com.raychal.githubusers.databinding.FragmentHomeBinding

class ShowState(private val stateId: Int) {

    fun loading(homeBinding: FragmentHomeBinding?, followBinding: FragmentFollowBinding?) {
        when (stateId) {
            1 -> {
                homeBinding?.baseEmptyHome?.baseEmptyUser?.visibility = View.GONE
                homeBinding?.baseLoading?.shimmer?.visibility = View.VISIBLE
                homeBinding?.rvUser?.visibility = View.GONE
            }
            2 -> {
                followBinding?.baseEmptyFollow?.baseEmptyUser?.visibility = View.GONE
                followBinding?.baseLoader?.shimmer?.visibility = View.VISIBLE
                followBinding?.recylerFollow?.visibility = View.GONE
            }
        }
    }

    fun success(homeBinding: FragmentHomeBinding?, followBinding: FragmentFollowBinding?) {
        when (stateId) {
            1 -> {
                homeBinding?.baseEmptyHome?.baseEmptyUser?.visibility = View.GONE
                homeBinding?.baseLoading?.shimmer?.visibility = View.GONE
                homeBinding?.rvUser?.visibility = View.VISIBLE
            }
            2 -> {
                followBinding?.baseEmptyFollow?.baseEmptyUser?.visibility = View.GONE
                followBinding?.baseLoader?.shimmer?.visibility = View.GONE
                followBinding?.recylerFollow?.visibility = View.VISIBLE
            }
        }
    }

    fun error(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        message: String?,
        resources: Resources,
    ) {
        when (stateId) {
            1 -> {
                homeBinding?.baseEmptyHome?.baseEmptyUser?.visibility = View.VISIBLE
                homeBinding?.baseEmptyHome?.emptyText?.text =
                    message ?: resources.getString(R.string.not_found)
                homeBinding?.baseLoading?.shimmer?.visibility = View.GONE
                homeBinding?.rvUser?.visibility = View.GONE
            }
            2 -> {
                followBinding?.baseEmptyFollow?.baseEmptyUser?.visibility = View.VISIBLE
                followBinding?.baseEmptyFollow?.emptyText?.text =
                    message ?: resources.getString(R.string.not_found)
                followBinding?.baseLoader?.shimmer?.visibility = View.GONE
                followBinding?.recylerFollow?.visibility = View.GONE
            }
        }
    }
}