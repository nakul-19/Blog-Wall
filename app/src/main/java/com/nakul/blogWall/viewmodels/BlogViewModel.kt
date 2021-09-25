package com.nakul.blogWall.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nakul.blogWall.models.blog.SingleBlogResponse
import com.nakul.blogWall.models.like_and_comment.LikeModel
import com.nakul.blogWall.models.network_event.Auth
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.network.ApiException
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.network.TokenManager
import com.nakul.blogWall.repositories.BlogRepository
import com.nakul.blogWall.utils.UtilConstants
import kotlinx.coroutines.launch

class BlogViewModel(application: Application) : AndroidViewModel(application) {

    private val mBlog = MutableLiveData<Event<SingleBlogResponse>>()
    val blog: LiveData<Event<SingleBlogResponse>>
    private val repository: BlogRepository
    private val tm: TokenManager
    private val mLike = MutableLiveData<Event<Boolean>>()
    val like: LiveData<Event<Boolean>>
    val auth: LiveData<Auth>

    init {
        mBlog.value = Event.Loading()
        blog = mBlog
        mLike.value = Event.Loading()
        like = mLike
        repository = BlogRepository(BlogInterface())
        tm = TokenManager(
            application.getSharedPreferences(
                UtilConstants.fileName,
                Context.MODE_PRIVATE
            ), AuthInterface()
        )
        auth = tm.auth
    }

    fun getBlog(id: Int) {
        mBlog.value = Event.Loading()
        viewModelScope.launch {
            try {
                val r = repository.getBlogById(id)
                mBlog.postValue(Event.Success(r))
            } catch (e: Exception) {
                if (e is ApiException)
                    mBlog.postValue(Event.Error(e.message.toString()))
                else
                    mBlog.postValue(Event.Error("No Internet."))
            }
        }
    }

    fun likeBlog(id: Int, likes: Int = 1) {
        mLike.value = Event.Loading()
        viewModelScope.launch {
            try {
                repository.likeBlog("Bearer "+tm.accessToken, id, LikeModel(likes))
                mLike.postValue(Event.Success(true))
            } catch (e: Exception) {
                if (e is ApiException) {
                    if (e.code==401) {
                        tm.refresh(viewModelScope) { this@BlogViewModel.likeBlog(id, likes) }
                    } else {
                        mLike.postValue(Event.Error(e.message.toString()))
                    }
                } else
                    mLike.postValue(Event.Error("No Internet."))
            }
        }
    }

}