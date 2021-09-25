package com.nakul.blogWall.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nakul.blogWall.models.blog.Blog
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.network.ApiException
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.network.TokenManager
import com.nakul.blogWall.repositories.BlogRepository
import com.nakul.blogWall.utils.UtilConstants
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sp: SharedPreferences
    private val tm: TokenManager
    private val repository = BlogRepository(BlogInterface())

    private val _trending = MutableLiveData<Event<ArrayList<Blog>>>()
    val trending: LiveData<Event<ArrayList<Blog>>>

    init {
        sp = application.getSharedPreferences(UtilConstants.fileName, Context.MODE_PRIVATE)
        tm = TokenManager(sp, AuthInterface())
        _trending.value = Event.Loading()
        trending = _trending
    }

    fun getTrendingBlogs() {
        viewModelScope.launch {
            try {
                val res = repository.getTrendingBlogs()
                if (res.results.isNullOrEmpty())
                    return@launch
                _trending.postValue(Event.Success(res.results,""))
            } catch (e:Exception) {
                if (e is ApiException)
                    _trending.postValue(Event.Error(e.message.toString()))
                else
                    _trending.postValue(Event.Error("No Internet."))
            }
        }
    }

    fun setImage(call:(url: String?) -> Unit) {
        val dp = sp.getString(UtilConstants.userImage,"")
        call.invoke(dp)
    }

    fun getName(): String {
        val name = sp.getString(UtilConstants.name,"")?:""
        return name
    }

    fun logout() {
        sp.edit().clear().commit()
    }

}