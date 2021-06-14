package com.stark.shopelite.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.stark.shopelite.R

class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        auth = FirebaseAuth.getInstance()
        Handler().postDelayed({
            val currentUser = auth.currentUser
            val intent: Intent = if (currentUser == null) Intent(this@SplashScreen, AuthenticationActivity::class.java) else Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    /*override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        val intent: Intent = if (currentUser == null) Intent(this@SplashScreen, AuthenticationActivity::class.java) else Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)
        finish()
    }*/
}