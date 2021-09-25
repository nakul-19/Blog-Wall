package com.nakul.blogWall.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nakul.blogWall.MyApplication
import com.nakul.blogWall.models.like_and_comment.Comment
import com.nakul.blogWall.models.like_and_comment.CommentingResponse
import com.nakul.blogWall.models.network_event.Auth
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.models.user.User
import com.nakul.blogWall.network.ApiException
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.network.TokenManager
import com.nakul.blogWall.repositories.BlogRepository
import com.nakul.blogWall.utils.UtilConstants
import com.nakul.blogWall.utils.UtilFunctions.format
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class CommentViewModel(application: Application): AndroidViewModel(application) {


    private val repository: BlogRepository
    private val tm: TokenManager
    private val mComment = MutableLiveData<Event<Comment>>()
    val comment: LiveData<Event<Comment>>
    val auth: LiveData<Auth>

    init {
        mComment.value = Event.Waiting()
        comment = mComment
        repository = BlogRepository(BlogInterface())
        tm = TokenManager(
            application.getSharedPreferences(
                UtilConstants.fileName,
                Context.MODE_PRIVATE
            ), AuthInterface()
        )
        auth = tm.auth
    }

    fun commentBlog(id: Int, content: String) {
        mComment.value = Event.Loading()
        viewModelScope.launch {
            try {
                val response=repository.commentBlog("Bearer "+tm.accessToken, id, content)
                mComment.postValue(Event.Success(handleCommentResponse(response)))
            } catch (e: Exception) {
                Log.d("CommentViewModel","problem: "+e.message.toString())
                if (e is ApiException) {
                    if (e.code==401) {
                        tm.refresh(viewModelScope) { this@CommentViewModel.commentBlog(id, content) }
                    } else {
                        mComment.postValue(Event.Error(e.message.toString()))
                    }
                } else
                    mComment.postValue(Event.Error("No Internet."))
            }
        }
    }

    private fun handleCommentResponse(response: CommentingResponse): Comment {
        val sp = getApplication<MyApplication>().getSharedPreferences(UtilConstants.fileName,Context.MODE_PRIVATE)
        val dp = sp.getString(UtilConstants.userImage,"")?:""
        val userId = sp.getInt(UtilConstants.userId,0)?:0
        val userName = sp.getString(UtilConstants.name,"")?:""
        val timeStamp = response.timeStamp?:Date.from(Instant.now()).format("yyyy-MM-dd")
        return Comment(0,0,0,response.content,timeStamp,User("","",userId,dp!!,userName!!))
    }

}