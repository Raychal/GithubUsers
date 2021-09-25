package com.raychal.githubusers.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
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
        appBarConfiguration = AppBarConfiguration(nController.graph)
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
        return nController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(nController) || super.onOptionsItemSelected(item)
    }
}
