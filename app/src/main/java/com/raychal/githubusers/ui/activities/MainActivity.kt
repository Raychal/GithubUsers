package com.raychal.githubusers.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        setSupportActionBar(mainBinding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        nController = navHostFragment.navController
        NavigationUI.setupWithNavController(mainBinding.bottomNavView, nController)
        appBarConfiguration = AppBarConfiguration(nController.graph)
        nController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.details_destination -> mainBinding.bottomNavView.visibility = View.GONE
                else -> mainBinding.bottomNavView.visibility = View.VISIBLE
            }
        }
        setupActionBarWithNavController(nController, appBarConfiguration)

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

    override fun onSupportNavigateUp(): Boolean {
        nController.navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }
}
