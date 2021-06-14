package com.stark.shopelite.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stark.shopelite.R
import com.stark.shopelite.fragment.SignInFragment
import com.stark.shopelite.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    companion object {
        @JvmStatic
        var isResetPasswordFragment = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setFragment(SignInFragment())
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (isResetPasswordFragment) {
            isResetPasswordFragment = false
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_out_from_right, R.anim.slide_from_left)
            fragmentTransaction.replace(binding.authenticationFrameLayout.id, SignInFragment())
            fragmentTransaction.commit()
        }
        else super.onBackPressed()
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.authenticationFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }
}