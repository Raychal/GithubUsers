package com.raychal.githubusers.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.raychal.githubusers.R
import com.raychal.githubusers.databinding.FragmentHomeBinding
import com.raychal.githubusers.ui.adapter.UserAdapter
import com.raychal.githubusers.viewmodel.HomeViewModel
import com.raychal.githubusers.utils.state.ShowState
import com.raychal.githubusers.utils.state.State
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeAdapter: UserAdapter
    private lateinit var homeViewModel: HomeViewModel
    private val showState = ShowState(stateHomeId)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(HomeViewModel::class.java)

        val myTitle = resources.getString(R.string.app_name)
        Log.d("title", myTitle)

        homeBinding.textTitle.text = myTitle

        homeBinding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                Log.e("AppBarLayout", "collapsed")
                homeBinding.toolbarLayout.title = myTitle
            } else {
                Log.e("AppBarLayout", "expanded")
                homeBinding.toolbarLayout.title = ""
            }
        })

        homeAdapter = UserAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeDestinationToDetailsDestination(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }

        homeBinding.rvUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }

        homeBinding.svSearch.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.setSearch(query)
                    homeBinding.svSearch.clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }
        observeHome()
    }
    private fun observeHome() {
        homeViewModel.searchResult.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.state) {
                    State.SUCCESS -> {
                        resource.data?.let { users ->
                            if (!users.isNullOrEmpty()) {
                                showState.success(homeBinding, null)
                                homeAdapter.setData(users)
                            } else {
                                showState.error(homeBinding,null, null, resources)
                            }
                        }
                    }
                    State.LOADING -> showState.loading(homeBinding, null)
                    State.ERROR -> showState.error(homeBinding, null, it.message, resources
                    )
                }
            }
        })
    }

    companion object {
        const val stateHomeId = 1
    }
}