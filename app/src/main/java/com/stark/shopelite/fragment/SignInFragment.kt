package com.stark.shopelite.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.stark.shopelite.activity.AuthenticationActivity.Companion.isResetPasswordFragment
import com.stark.shopelite.activity.MainActivity
import com.stark.shopelite.R
import com.stark.shopelite.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private  lateinit var binding: FragmentSignInBinding

    private var parentFrameLayout: FrameLayout? = null
    private var auth: FirebaseAuth? = null
    private val emailPattern = "[a-zA-Z0-9_-]+@[a-z]+.[a-z]+"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        parentFrameLayout = requireActivity().findViewById(R.id.authentication_frame_layout)
        auth = FirebaseAuth.getInstance()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.notHaveAccount.setOnClickListener { v: View? -> setFragment(SignUpFragment()) }
        binding!!.signInEmailId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInputs()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.signInPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInputs()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.signInButton.setOnClickListener { v: View? -> checkEmailAndPassword() }
        binding!!.signInCloseButton.setOnClickListener { v: View? -> mainActivityIntent() }
        binding!!.signForgotPassword.setOnClickListener { v: View? ->
            isResetPasswordFragment = true
            setFragment(ResetPasswordFragment())
        }
    }

    private fun checkEmailAndPassword() {
        if (binding.signInEmailId.text.toString().matches(emailPattern.toRegex())) {
            if (binding!!.signInPassword.length() >= 8) {
                binding!!.signInProgressBar.visibility = View.VISIBLE
                binding!!.signInButton.isEnabled = false
                auth!!.signInWithEmailAndPassword(binding!!.signInEmailId.text.toString(), binding!!.signInPassword.text.toString())
                        .addOnCompleteListener { task: Task<AuthResult?> ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, "Successfully Login", Toast.LENGTH_SHORT).show()
                                mainActivityIntent()
                            } else {
                                binding!!.signInProgressBar.visibility = View.GONE
                                binding!!.signInButton.isEnabled = true
                                val error = task.exception!!.message
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
            } else Toast.makeText(context, "Incorrect Email or Password!", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "Incorrect Email or Password!", Toast.LENGTH_SHORT).show()
    }

    private fun checkInputs() {
        if (!TextUtils.isEmpty(binding!!.signInEmailId.text)) {
            binding!!.signInButton.isEnabled = !TextUtils.isEmpty(binding!!.signInPassword.text)
        } else binding!!.signInButton.isEnabled = false
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_from_left)
        fragmentTransaction.replace(parentFrameLayout!!.id, fragment)
        fragmentTransaction.commit()
    }

    private fun mainActivityIntent() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}