package com.nakul.blogWall.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nakul.blogWall.models.Blog

class CategoryViewModel : ViewModel() {

    private val mBlogList = MutableLiveData<ArrayList<Blog>>()
    var blogList: LiveData<ArrayList<Blog>> = mBlogList

    fun getList(category: String) {

    }

    fun clearList() {
        mBlogList.postValue(ArrayList())
    }
}