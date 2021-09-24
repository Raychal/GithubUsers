package com.raychal.githubusers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raychal.githubusers.R
import com.raychal.githubusers.databinding.FragmentFavoriteBinding
import com.raychal.githubusers.ui.adapter.UserAdapter
import com.raychal.githubusers.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: UserAdapter
    private val favoriteViewModel: FavoriteViewModel by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = context?.resources?.getString(R.string.favorite)
        favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = UserAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteDestinationToDetailsDestination(username),
                FragmentNavigatorExtras(iv to username)
            )
        }

        favoriteBinding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        observeFavorite()
    }

    private fun observeFavorite() {
        favoriteLoading(favoriteBinding)
        favoriteViewModel.dataFavorite.observe(viewLifecycleOwner, {
            it?.let { users ->
                if (!users.isNullOrEmpty()){
                    favoriteSuccess(favoriteBinding)
                    favoriteAdapter.setData(users)
                } else {
                    favoriteError(
                        favoriteBinding,
                        resources.getString(R.string.not_have, "", resources.getString(R.string.favorite))
                    )
                }
            }
        })
    }

    private fun favoriteLoading(favoriteFragmentBinding: FragmentFavoriteBinding){
            favoriteFragmentBinding.baseEmptyFav.baseEmptyUser.visibility = View.GONE
            favoriteFragmentBinding.baseLoaderFav.shimmer.visibility = View.VISIBLE
            favoriteFragmentBinding.rvFavorite.visibility = View.GONE
    }

    private fun favoriteSuccess(favoriteFragmentBinding: FragmentFavoriteBinding) {
            favoriteFragmentBinding.baseEmptyFav.baseEmptyUser.visibility = View.GONE
            favoriteFragmentBinding.baseLoaderFav.shimmer.visibility = View.GONE
            favoriteFragmentBinding.rvFavorite.visibility = View.VISIBLE
    }

    private fun favoriteError(
        favoriteFragmentBinding: FragmentFavoriteBinding,
        message: String?
    ){
            favoriteFragmentBinding.baseEmptyFav.baseEmptyUser.visibility = View.VISIBLE
            favoriteFragmentBinding.baseEmptyFav.emptyText.text =
                message ?: resources.getString(R.string.not_found)
            favoriteFragmentBinding.baseLoaderFav.shimmer.visibility = View.GONE
            favoriteFragmentBinding.rvFavorite.visibility = View.GONE
    }
}