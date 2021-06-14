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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.stark.shopelite.activity.MainActivity
import com.stark.shopelite.R
import com.stark.shopelite.databinding.FragmentSignUpBinding
import java.util.*

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private var parentFrameLayout: FrameLayout? = null
    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null
    private val emailPattern = "[a-zA-Z0-9_-]+@[a-z]+.[a-z]+"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        parentFrameLayout = requireActivity().findViewById(R.id.authentication_frame_layout)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.signUpAlreadHaveAccount.setOnClickListener { v: View? -> setFragment(SignInFragment()) }
        binding!!.signUpEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInput()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.signUpFullname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInput()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.signUpPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInput()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.signUpConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInput()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.signUpBtn.setOnClickListener { v: View? -> checkEmailAndPassword() }
        binding!!.signInCloseButton.setOnClickListener { v: View? -> mainActivityIntent() }
    }

    private fun checkEmailAndPassword() {
        if (binding!!.signUpEmail.text.toString().matches(emailPattern.toRegex())) {
            if (binding!!.signUpPassword.text.toString() == binding!!.signUpConfirmPassword.text.toString()) {
                binding!!.signUpProgressBar.visibility = View.VISIBLE
                binding!!.signUpBtn.isEnabled = false
                auth!!.createUserWithEmailAndPassword(binding!!.signUpEmail.text.toString(), binding!!.signUpPassword.text.toString())
                        .addOnCompleteListener { task: Task<AuthResult?> ->
                            if (task.isSuccessful) {
                                val userData: MutableMap<Any, String> = HashMap()
                                userData["fullname"] = binding!!.signUpFullname.text.toString()
                                firestore!!.collection(USER_COLLECTION_NAME)
                                        .add(userData)
                                        .addOnCompleteListener { task1: Task<DocumentReference?> ->
                                            if (task1.isSuccessful) {
                                                mainActivityIntent()
                                            } else {
                                                val error = task1.exception!!.message
                                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                            } else {
                                binding!!.signUpProgressBar.visibility = View.GONE
                                binding!!.signUpBtn.isEnabled = true
                                val error = task.exception!!.message
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
            } else {
                binding!!.signUpConfirmPassword.error = "Password doesn't match "
            }
        } else {
            binding!!.signUpEmail.error = "Invalid Email !"
        }
    }

    private fun checkInput() {
        if (!TextUtils.isEmpty(binding!!.signUpEmail.text)) {
            if (!TextUtils.isEmpty(binding!!.signUpFullname.text)) {
                if (!TextUtils.isEmpty(binding!!.signUpPassword.text) && binding!!.signUpPassword.length() > 8) {
                    binding!!.signUpBtn.isEnabled = !TextUtils.isEmpty(binding!!.signUpConfirmPassword.text)
                } else binding!!.signUpBtn.isEnabled = false
            } else binding!!.signUpBtn.isEnabled = false
        } else binding!!.signUpBtn.isEnabled = false
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_out_from_right)
        fragmentTransaction.replace(parentFrameLayout!!.id, fragment)
        fragmentTransaction.commit()
    }

    private fun mainActivityIntent() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        const val USER_COLLECTION_NAME = "USER"
    }
}