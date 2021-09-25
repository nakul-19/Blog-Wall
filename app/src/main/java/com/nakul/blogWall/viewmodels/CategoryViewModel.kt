package com.nakul.blogWall.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakul.blogWall.models.blog.BlogResponse
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.network.ApiException
import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.repositories.BlogRepository
import kotlinx.coroutines.launch

class CategoryViewModel() : ViewModel() {

    private val mBlogListResponse = MutableLiveData<Event<BlogResponse>>()
    val blogListResponse: LiveData<Event<BlogResponse>>

    private val repository: BlogRepository

    init {
        mBlogListResponse.value = Event.Loading()
        blogListResponse = mBlogListResponse
        repository = BlogRepository(BlogInterface())
    }

    fun getList(offset: Int, category: String) {
        mBlogListResponse.postValue(Event.Loading())

        var c = category
        if (c[0]=='H')
            c=c.substringBefore(' ')

        viewModelScope.launch {
            try {
                val res = repository.getCategoriesBlogs(offset, c)
                mBlogListResponse.postValue(Event.Success(res))
            } catch (e: Exception) {
                if (e is ApiException) {
                    mBlogListResponse.postValue(Event.Error(e.message.toString()))
                } else {
                    mBlogListResponse.postValue(Event.Error("No Internet."))
                }
            }
        }
    }

}