package com.nakul.blogWall.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.nakul.blogWall.R
import com.nakul.blogWall.databinding.ActivitySplashBinding
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.repositories.AuthRepository
import com.nakul.blogWall.utils.UtilConstants

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val anim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                Handler().postDelayed({
                    val loggedin = AuthRepository(AuthInterface()).checkLogin(
                        getSharedPreferences(
                            UtilConstants.fileName,
                            Context.MODE_PRIVATE
                        )
                    )
                    if (!loggedin)
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    else
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    this@SplashActivity.finish()
                }, 1000L)
            }

            override fun onAnimationRepeat(p0: Animation?) {}

        })
        binding.linearLayout.startAnimation(anim)
    }
}