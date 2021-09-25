package com.nakul.blogWall.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nakul.blogWall.models.blog.NewBlogModel
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.network.ApiException
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.network.TokenManager
import com.nakul.blogWall.repositories.BlogRepository
import com.nakul.blogWall.utils.UtilConstants
import kotlinx.coroutines.launch

class NewBlogViewModel(application: Application) : AndroidViewModel(application) {
    private val sp: SharedPreferences
    private val tm: TokenManager
    private val repository = BlogRepository(BlogInterface())

    private val mResponse = MutableLiveData<Event<Any>>()
    val response: LiveData<Event<Any>>

    init {
        sp = application.getSharedPreferences(UtilConstants.fileName, Context.MODE_PRIVATE)
        tm = TokenManager(sp, AuthInterface())

        mResponse.value = Event.Waiting()
        response = mResponse
    }

    fun createBlog(topic: String, title: String, content: String, publish: String) {
        mResponse.postValue(Event.Loading())
        viewModelScope.launch {
            try {
                val res = repository.newBlog(
                    "Bearer " + tm.accessToken,
                    NewBlogModel(topic, title, content, publish)
                )
                mResponse.postValue(Event.Success(res))
            } catch (e: Exception) {
                if (e is ApiException) {
                    if (e.code == 401) {
                        tm.refresh(viewModelScope) {
                            this@NewBlogViewModel.createBlog(
                                topic,
                                title,
                                content,
                                publish
                            )
                        }
                    } else {
                        mResponse.postValue(Event.Error(e.message.toString()))
                    }
                } else
                    mResponse.postValue(Event.Error("No Internet."))
            }
        }
    }

}