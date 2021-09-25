package com.nakul.blogWall.adapters

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScroller(var recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    val lm = recyclerView.layoutManager!! as LinearLayoutManager

    abstract fun isLast(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = lm.childCount
        val totalItemCount = recyclerView.adapter?.itemCount?:0
        val firstVisibleItemPosition = lm.findFirstVisibleItemPosition()

        if (!isLoading() && !isLast()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                Log.d("scrolled", "$visibleItemCount $totalItemCount $firstVisibleItemPosition")
                loadMore()
            }
        }
    }

    abstract fun loadMore()
}