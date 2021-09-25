package com.raychal.githubusers.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.raychal.githubusers.R
import com.raychal.githubusers.databinding.ActivityMainBinding
import com.raychal.githubusers.ui.adapter.UserAdapter
import com.raychal.githubusers.ui.fragments.HomeFragmentDirections
import com.raychal.githubusers.utils.viewUtils.changeNavigation
import com.raychal.githubusers.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var usersAdapter: UserAdapter
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var nController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setToolbar()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        nController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(nController.graph)
        setupActionBarWithNavController(nController, appBarConfiguration)

//        if (nController != null){
//            Log.d("text", "nController not null")
//        } else if (appBarConfiguration != null){
//            Log.d("text", "appBarConfiguration not null")
//        } else if (nController.graph != null){
//            Log.d("text", "nController.graph not null")
//        } else if (mainBinding != null){
//            Log.d("text", "mainBinding not null")
//        } else {
//            Log.d("text", "all not null")
//        }

        userViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)
        userViewModel.userList.observe(this, { users ->
            if (!users.isNullOrEmpty()) {
                usersAdapter.setData(users)
            }
        })
    }

    private fun setToolbar(){
        mainBinding.apply {
            setSupportActionBar(mainBinding.toolbar)
            toolbar.apply {
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.favorite_destination -> {
                            val action =
                                HomeFragmentDirections.actionHomeDestinationToFavoriteDestination()
                            changeNavigation(action)
                        }
                        R.id.settings_destination -> {
                            val action =
                                HomeFragmentDirections.actionHomeDestinationToSettingsFragment()
                            changeNavigation(action)
                        }
                    }
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        nController.navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }
}
