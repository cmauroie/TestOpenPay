package com.fit.testopenpay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.fit.map.presentation.MapsFragment
import com.fit.map.presentation.MapsFragment.Companion.REQUEST_CODE_LOCATION
import com.fit.map.presentation.MapsFragment.Companion.REQUEST_CODE_POST_NOTIFICATIONS
import com.fit.movies.presentation.MoviesFragment
import com.fit.photo.presentation.PhotoFragment
import com.fit.popularperson.presentation.PopularPersonFragment
import com.fit.testopenpay.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val popularPersonFragment = PopularPersonFragment()
    private val moviesFragment = MoviesFragment()
    private val mapsFragment = MapsFragment()
    private val photoFragment = PhotoFragment()

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
                R.id.navigation_photos
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

                R.id.navigation_photos -> {
                    showFragment(photoFragment)
                    true
                }

                else -> false
            }
        }

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION, REQUEST_CODE_POST_NOTIFICATIONS -> {
                mapsFragment.permissionManager.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults
                )
            }
        }
    }

}