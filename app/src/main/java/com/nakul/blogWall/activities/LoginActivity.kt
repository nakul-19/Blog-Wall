package com.nakul.blogWall.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.nakul.blogWall.R
import com.nakul.blogWall.databinding.ActivityLoginBinding
import com.nakul.blogWall.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setContentView(binding.root)
        binding.loginSwitch.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        setClick()
    }

    private fun setClick() {
        binding.lButton.setOnClickListener {
            if ((it as Button).text.equals("SIGN IN"))
                viewModel.signInUser()
            else
                viewModel.signUpUser()
        }

        binding.loginSwitch.setOnClickListener {
            if (binding.lButton.text.equals("SIGN IN"))
                setRegister()
            else
                setLogin()
        }
    }

    private fun setRegister() {
        binding.password.animate()
            .setDuration(100L)
            .translationY(binding.password.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.password.translationY = 0f
                    binding.nameContainer.visibility = View.VISIBLE
                    binding.lButton.text = getString(R.string.sign_up)
                    binding.loginSentence.text = getString(R.string.already_have_an_account)
                    binding.loginSwitch.text = getString(R.string.sign_in)
                }
            })
    }

    private fun setLogin() {
        binding.passwordContainer.animate()
            .setDuration(100L)
            .translationY(-binding.nameContainer.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator?) {
                    binding.nameContainer.visibility = View.INVISIBLE
                    super.onAnimationStart(animation)
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.nameContainer.visibility = View.GONE
                    binding.passwordContainer.translationY = 0f
                    binding.lButton.text = getString(R.string.sign_in)
                    binding.loginSentence.text = getString(R.string.don_t_have_an_account)
                    binding.loginSwitch.text = getString(R.string.sign_up)
                }
            })
    }
}