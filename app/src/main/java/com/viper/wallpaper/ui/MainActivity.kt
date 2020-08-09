package com.viper.wallpaper.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.viper.wallpaper.R
import com.viper.wallpaper.ui.navigation.KeepStateNavigator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavBottom()
    }

    private fun setUpNavBottom() {
        navController = findNavController(R.id.fragment)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.fragment)!!
        val navigator = KeepStateNavigator(this, hostFragment.childFragmentManager,
            R.id.fragment
        )
        navController.apply {
            navigatorProvider.addNavigator(navigator)
            setGraph(R.navigation.tab_navigation)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_menu, menu)
        nv_bottom_menu.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
}