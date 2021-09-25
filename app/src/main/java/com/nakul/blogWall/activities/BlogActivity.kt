package com.nakul.blogWall.activities

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.databinding.ActivityBlogBinding
import com.nakul.blogWall.models.blog.Blog
import com.nakul.blogWall.models.blog.SingleBlogResponse
import com.nakul.blogWall.models.network_event.Auth
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.viewmodels.BlogViewModel

class BlogActivity : AppCompatActivity() {

    companion object {
        var model: Blog? = null
        var extendedModel: SingleBlogResponse? = null

        fun start(c: Context, m: Blog?) {
            model = m
            c.startActivity(Intent(c, BlogActivity::class.java))
        }
    }

    lateinit var b: ActivityBlogBinding
    private val viewmodel by viewModels<BlogViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityBlogBinding.inflate(layoutInflater)
        setContentView(b.root)

        setView()
        setObserver()
        setClick()
    }

    private fun setObserver() {
        viewmodel.blog.observe(this) {
            when (it) {
                is Event.Error -> {
                    Snackbar.make(b.root, it.msg, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                        viewmodel.getBlog(model!!.id)
                    }.show()
                }
                is Event.Success -> {
                    extendedModel = it.r
                    b.actionDetails.visibility = View.VISIBLE
                    b.likeButton.visibility = View.VISIBLE
                    b.showComments.visibility = View.VISIBLE
                    var like = 0
                    for (i in it.r.likes)
                        like += i.like
                    b.likes.text = like.toString()
                    b.comments.text = it.r.comments.size.toString()
                }
                else -> {
                }
            }
        }

        viewmodel.like.observe(this) {
            Log.d("response like", it.toString())
            when (it) {
                is Event.Success -> {
                    b.likes.text = (b.likes.text.toString().toInt() + 1).toString()
                }
                is Event.Error -> {
                    Snackbar.make(b.root, it.msg, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                        viewmodel.likeBlog(model!!.id)
                    }.show()
                    Log.d("response like", it.msg)
                }
                else -> {
                }
            }
        }

        viewmodel.auth.observe(this) {
            when (it) {
                is Auth.NetworkError -> {
                    Snackbar.make(b.root, it.msg, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                        viewmodel.likeBlog(model!!.id)
                    }.show()
                }
                is Auth.Unauthenticated -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    finishAffinity()
                    startActivity(intent)
                }
            }
        }
    }

    private fun setClick() {
        var animating = false
        b.likeButton.setOnClickListener {
            if (animating)
                return@setOnClickListener

            viewmodel.likeBlog(model!!.id)

            b.likeBubble.apply {
                visibility = View.VISIBLE
                alpha = 0f
                animating = true
                animate()
                    .alpha(1f)
                    .setDuration(450L)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationEnd(p0: Animator?) {
                            animate()
                                .alpha(0f)
                                .setDuration(300L)
                                .setListener(object : Animator.AnimatorListener {
                                    override fun onAnimationEnd(p0: Animator?) {
                                        visibility = View.GONE
                                        animating = false
                                    }

                                    override fun onAnimationStart(p0: Animator?) {}
                                    override fun onAnimationCancel(p0: Animator?) {}
                                    override fun onAnimationRepeat(p0: Animator?) {}
                                })
                                .start()
                        }

                        override fun onAnimationStart(p0: Animator?) {}
                        override fun onAnimationCancel(p0: Animator?) {}
                        override fun onAnimationRepeat(p0: Animator?) {}

                    })
                    .start()

            }
        }

        b.showComments.setOnClickListener {
            if (extendedModel!=null)
                CommentActivity.start(this, extendedModel!!)
        }
    }

    override fun onStart() {
        super.onStart()
        viewmodel.getBlog(model!!.id)
    }

    private fun setView() {
        if (model != null) {
            b.heading.text = model!!.title
            b.authorName.text = model!!.owner.name
            b.content.text = model!!.content
            Glide.with(b.root.context).load(model!!.owner.profile_pic)
                .into(b.authorImage)
            Glide.with(b.root.context).load(model!!.image).into(b.image)
        }
    }
}