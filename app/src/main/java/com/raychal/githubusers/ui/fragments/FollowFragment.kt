package com.raychal.githubusers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raychal.githubusers.R
import com.raychal.githubusers.ui.adapter.UserAdapter
import com.raychal.githubusers.databinding.FragmentFollowBinding
import com.raychal.githubusers.viewmodel.FollowViewModel
import com.raychal.githubusers.utils.state.ShowState
import com.raychal.githubusers.utils.state.State
import com.raychal.githubusers.utils.state.TypeView

class FollowFragment : Fragment() {
    private lateinit var followBinding: FragmentFollowBinding
    private lateinit var usersAdapter: UserAdapter
    private lateinit var followViewModel: FollowViewModel
    private lateinit var username: String
    private var type: String? = null
    private var showState = ShowState(stateFollowId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME).toString()
            type = it.getString(TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followBinding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return followBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersAdapter = UserAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                DetailFragmentDirections.actionFollowToDetailsDestination(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }

        followBinding.recylerFollow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        followViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        when (type) {
            resources.getString(R.string.following) -> followViewModel.setFollow(username, TypeView.FOLLOWING)
            resources.getString(R.string.follower) -> followViewModel.setFollow(username, TypeView.FOLLOWER)
            else -> showState.error(null, followBinding, null, resources)
        }
        observeFollow()
    }

    private fun observeFollow() {
        followViewModel.dataFollow.observe(viewLifecycleOwner, {
            when (it.state) {
                State.SUCCESS ->
                    if (!it.data.isNullOrEmpty()) {
                        showState.success(null, followBinding)
                        usersAdapter.run { setData(it.data) }
                    } else {
                        showState.error(null,
                            followBinding,
                            resources.getString(R.string.not_have, username, type),
                            resources)
                    }
                State.LOADING -> showState.loading(null, followBinding)
                State.ERROR -> showState.error(null, followBinding, it.message, resources)
            }
        })
    }
    companion object {
        fun newInstance(username: String, type: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                    putString(TYPE, type)
                }
            }
        const val stateFollowId = 2
        private const val TYPE = "type"
        private const val USERNAME = "username"
    }
}