package com.stark.shopelite.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.stark.shopelite.R
import com.stark.shopelite.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgotPasswordEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInput()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.forgotPasswordGoBack.setOnClickListener { v: View? -> setFragment(SignInFragment()) }
        binding.resetPasswordBtn.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.forgotPasswordItemContainer)
            binding.forgotPasswordEmailIcon.visibility = View.VISIBLE
            binding.forgotPasswordProgressBar.visibility = View.VISIBLE
            binding.resetPasswordBtn.isEnabled = false

            auth.sendPasswordResetEmail(binding.forgotPasswordEmail.text.toString())
                    .addOnCompleteListener { task: Task<Void?> ->
                        binding.forgotPasswordProgressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            binding.forgotPasswordEmailIconTv.visibility = View.VISIBLE
                            binding.forgotPasswordEmailIconTv.setTextColor(resources.getColor(R.color.green))
                            binding.forgotPasswordEmailIconTv.text = "Recovery email has been send  Check your inbox."
                            binding.forgotPasswordEmailIcon.imageTintList = ColorStateList.valueOf(resources.getColor(
                                R.color.red
                            ))
                        } else {
                            val error = task.exception!!.message
                            binding.forgotPasswordEmailIconTv.text = error
                            binding.forgotPasswordEmailIconTv.setTextColor(resources.getColor(R.color.red))
                            binding.forgotPasswordEmailIcon.imageTintList = ColorStateList.valueOf(resources.getColor(
                                R.color.red
                            ))
                            TransitionManager.beginDelayedTransition(binding.forgotPasswordItemContainer)
                            binding.forgotPasswordEmailIconTv.visibility = View.VISIBLE
                        }
                        binding!!.resetPasswordBtn.isEnabled = true
                    }
        }
    }

    private fun checkInput() {
        binding.resetPasswordBtn.isEnabled = !TextUtils.isEmpty(binding.forgotPasswordEmail.text)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_from_left)
        fragmentTransaction.replace(binding.parentFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }
}