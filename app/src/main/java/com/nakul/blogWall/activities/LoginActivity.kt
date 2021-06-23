package com.nakul.blogWall.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nakul.blogWall.R
import com.nakul.blogWall.databinding.ActivityLoginBinding
import com.nakul.blogWall.fragments.SignInFragment
import com.nakul.blogWall.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.login_frame, SignInFragment())
            .commit()
    }

}