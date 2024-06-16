package com.fit.testopenpay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fit.map.MapsFragment
import com.fit.movies.presentation.MoviesFragment
import com.fit.popularperson.presentation.PopularPersonFragment
import com.fit.testopenpay.databinding.ActivityMainBinding
import com.fit.testopenpay.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val popularPersonFragment = PopularPersonFragment()
    private val moviesFragment = MoviesFragment()
    private val mapsFragment = MapsFragment()
    private val notificationsFragment = NotificationsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_popularperson,
                R.id.navigation_movies,
                R.id.navigation_map,
                R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_popularperson -> {
                    showFragment(popularPersonFragment)
                    true
                }

                R.id.navigation_movies -> {
                    showFragment(moviesFragment)
                    true
                }

                R.id.navigation_map -> {
                    showFragment(mapsFragment)
                    true
                }

                R.id.navigation_notifications -> {
                    showFragment(notificationsFragment)
                    true
                }

                else -> false
            }
        }
        // Load the initial fragment
        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_popularperson
        }
    }
    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach { transaction.hide(it) }
        if (!fragment.isAdded) {
            transaction.add(R.id.fragment_container, fragment)
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
    }

}