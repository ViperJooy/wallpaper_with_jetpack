package com.viper.wallpaper.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.viper.wallpaper.R
import com.viper.wallpaper.ui.navigation.KeepStateNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
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

        val menu = PopupMenu(this,nv_bottom_menu).menu
        menuInflater.inflate(R.menu.bottom_menu,menu)
        nv_bottom_menu.setupWithNavController(menu,navController)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.bottom_menu, menu)
//        nv_bottom_menu.setupWithNavController(menu!!, navController)
//        return true
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        navController.navigateUp()
//        return true
//    }
}