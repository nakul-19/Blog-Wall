package com.nakul.blogWall.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScroller(var lm: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLast(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = lm.childCount
        val totalItemCount = lm.itemCount
        val firstVisibleItemPosition = lm.findFirstVisibleItemPosition()

        if (!isLoading() && !isLast()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMore()
            }
        }
    }

    abstract fun loadMore()
}