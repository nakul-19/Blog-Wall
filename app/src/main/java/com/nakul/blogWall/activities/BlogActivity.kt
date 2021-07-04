package com.nakul.blogWall.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nakul.blogWall.databinding.ActivityBlogBinding
import com.nakul.blogWall.models.blog.Blog

class BlogActivity : AppCompatActivity() {

    companion object {
        var model: Blog? = null

        fun start(c: Context, m: Blog?) {
            model = m
            c.startActivity(Intent(c, BlogActivity::class.java))
        }
    }

    lateinit var binding: ActivityBlogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        if (model != null) {
            binding.heading.text = model!!.title
            binding.authorName.text = model!!.owner.username
            binding.content.text = model!!.content
            Glide.with(binding.root.context).load(model!!.owner.profile_pic)
                .into(binding.authorImage)
            Glide.with(binding.root.context).load(model!!.image).into(binding.image)
        }
    }
}