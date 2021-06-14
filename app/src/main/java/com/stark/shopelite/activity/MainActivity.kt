package com.stark.shopelite.activity

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.stark.shopelite.fragment.HomeFragment
import com.stark.shopelite.R
import com.stark.shopelite.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        frameLayout = findViewById(R.id.main_frame_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        actionBarDrawerToggle.syncState()

        binding.drawerLayout.setScrimColor(View.GONE)
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
               /* if( binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                    frameLayout.alpha = 0.2F
                    Toast.makeText(this@MainActivity,"Me Called",Toast.LENGTH_SHORT).apply {
                        show()
                    }
                }
                else
                    binding.drawerLayout.alpha=1F

                */
            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })

        binding.navView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_order -> { Toast.makeText(this@MainActivity, "You just clicked in order", Toast.LENGTH_SHORT).show()
                     true
                }
                R.id.nav_reward -> true
                R.id.nav_cart -> true
                R.id.nav_wishlist -> true
                R.id.nav_account -> true
                R.id.nav_logout -> true
                else -> {
                }
            }
            item.isCheckable = true
            binding.drawerLayout.close()

            true
        }
        binding.navView.menu.getItem(0).setCheckable(true)

        setFragment(HomeFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
            }
            R.id.menu_notification -> {
            }
            R.id.menu_cart -> {
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(frameLayout.id, fragment)
        fragmentTransaction.commit()
    }
}