package com.example.ingsoftappmobiles.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var menu2:Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController:NavController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_albums, R.id.navigation_artists, R.id.navigation_collectors
            )
        )
        setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            menu2 = menu
        }
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val parts:List<String> = getSupportFragmentManager().fragments[0].childFragmentManager.fragments[0].javaClass.name.split(".")
        menu?.findItem(R.id.create_albums)?.setVisible(parts.last().equals("AlbumListFragment"))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.create_albums -> {
                // User chose the "Settings" item, show the app settings UI...
                val navController:NavController = findNavController(R.id.nav_host_fragment_activity_main)
                navController.navigate(R.id.action_navigation_albums_to_navigation_create_album)
                return true
            }

            else -> {
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
            }

        }
    }
}