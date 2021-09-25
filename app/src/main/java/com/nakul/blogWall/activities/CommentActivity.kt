package com.nakul.blogWall.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.R
import com.nakul.blogWall.adapters.CommentAdapter
import com.nakul.blogWall.databinding.ActivityCommentBinding
import com.nakul.blogWall.models.blog.SingleBlogResponse
import com.nakul.blogWall.models.like_and_comment.Comment
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.models.user.User
import com.nakul.blogWall.utils.UtilConstants
import com.nakul.blogWall.viewmodels.CommentViewModel

class CommentActivity : AppCompatActivity() {

    companion object {
        var model: SingleBlogResponse? = null
        fun start(c: Context, blog: SingleBlogResponse) {
            model = blog
            c.startActivity(Intent(c, CommentActivity::class.java))
        }
    }

    private val viewmodel by viewModels<CommentViewModel>()
    val list = ArrayList<Comment>()
    lateinit var adapter: CommentAdapter
    lateinit var b: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.title = "Comments"
        setClick()
        setObserver()
        setRecycler()
        setComments()
    }

    private fun setObserver() {
        viewmodel.comment.observe(this) {
            when(it) {
                is Event.Success-> {
                    b.addComment.setImageResource(R.drawable.ic_send)
                    b.newComment.text = null
                    list.add(0,it.r)
                    adapter.notifyDataSetChanged()
                    b.commentsRecycler.smoothScrollToPosition(0)
                }
                is Event.Loading -> {
                    Glide.with(this).load(R.raw.loading_gif).into(b.addComment)
                }
                is Event.Error -> {
                    b.addComment.setImageResource(R.drawable.ic_send)
                    Snackbar.make(b.root,it.msg,Snackbar.LENGTH_SHORT).setAction("Retry"){
                        viewmodel.commentBlog(model!!.id,b.newComment.text.toString())
                    }.show()
                }
            }
        }
    }

    private fun setClick() {
        b.addComment.setOnClickListener {
            if (viewmodel.comment.value!=Event.Loading<Comment>())
            viewmodel.commentBlog(model!!.id,b.newComment.text.toString())
        }
    }

    private fun setRecycler() {
        b.commentsRecycler.layoutManager = LinearLayoutManager(this)
        adapter = CommentAdapter(list)
        b.commentsRecycler.adapter = adapter
    }

    private fun setComments() {
        list.clear()
        if (!model?.comments.isNullOrEmpty())
        list.addAll(model!!.comments)
        adapter.notifyDataSetChanged()
    }
}