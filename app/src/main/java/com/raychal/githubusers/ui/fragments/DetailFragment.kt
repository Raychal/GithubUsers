package com.raychal.githubusers.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.raychal.githubusers.R
import com.raychal.githubusers.databinding.FragmentDetailBinding
import com.raychal.githubusers.model.GithubUser
import com.raychal.githubusers.viewmodel.DetailViewModel
import com.raychal.githubusers.utils.state.State
import com.shashank.sony.fancytoastlib.FancyToast

class DetailFragment : Fragment(), View.OnClickListener {

    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var detailViewModel: DetailViewModel
    lateinit var githubUser: GithubUser
    private val args: DetailFragmentArgs by navArgs()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(
            this
        )[DetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        detailBinding.lifecycleOwner = viewLifecycleOwner
        observeDetail()
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailBinding.content.transitionName = args.username
        detailBinding.fabFavorite.setOnClickListener(this)

        val tabList = arrayOf(
            resources.getString(R.string.follower),
            resources.getString(R.string.following)
        )
        pagerAdapter = PagerAdapter(tabList, args.username, this)
        detailBinding.pager.adapter = pagerAdapter

        TabLayoutMediator(detailBinding.tabs, detailBinding.pager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeDetail() {
        detailViewModel.data(args.username).observe(viewLifecycleOwner, {
            if (it.state == State.SUCCESS) {
                githubUser = it.data!!
                detailBinding.data = it.data
            }
            Log.d("state", it.state.toString())
            Log.d("state", args.username)
        })

        detailViewModel.isFavorite.observe(viewLifecycleOwner, {
            isFavorite = it
            changeFavorite(it)
        })
    }

    override fun onClick(v: View?) {
        if (!isFavorite){
            detailViewModel.addFavorite(githubUser)
            FancyToast.makeText(
                context, resources.getString(R.string.favorite_add, githubUser.login), Toast.LENGTH_SHORT, FancyToast.SUCCESS, false
            ).show()
        } else {
            detailViewModel.removeFavorite(githubUser)
            FancyToast.makeText(
                context, resources.getString(R.string.favorite_remove, githubUser.login), Toast.LENGTH_SHORT, FancyToast.ERROR, false
            ).show()
        }
    }

    private fun changeFavorite(condition: Boolean){
        if (condition){
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_unfavorite)
        }
    }

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = tabList.size

        override fun createFragment(position: Int): Fragment =
            FollowFragment.newInstance(username, tabList[position])
    }
}