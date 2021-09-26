package com.nakul.blogWall.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.R
import com.nakul.blogWall.adapters.BlogAdapter
import com.nakul.blogWall.databinding.ActivityCategoryBinding
import com.nakul.blogWall.models.blog.Blog
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.viewmodels.CategoryViewModel
import de.hdodenhof.circleimageview.CircleImageView

class CategoryActivity : AppCompatActivity() {

    companion object {
        var category = ""

        fun start(context: Context, c: String) {
            category = c
            context.startActivity(Intent(context, CategoryActivity::class.java))
        }
    }

    private lateinit var b: ActivityCategoryBinding
    private val viewModel by viewModels<CategoryViewModel>()
    private lateinit var adapter: BlogAdapter
    var allFetched = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = true
    private val list = ArrayList<Blog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(b.root)

        setAppBar()
        setRecycler()
        setObserver()
        fetchData()

    }


    private fun fetchData() {
        isLoading=true
        viewModel.getList(list.size, category)
    }

    private fun setObserver() {
        viewModel.blogListResponse.observe(this) {
            when (it) {
                is Event.Loading -> {
                    if (list.isEmpty())
                        startShimmer()
                    else {
                        b.loading.visibility=View.VISIBLE
                        b.blogRecycler.setPadding(0,0,0,30)
                    }
                    isLoading=true
                }
                is Event.Success -> {
                    isLoading=false
                    if (it.r.results.isEmpty()) {
                        allFetched = true
                        b.loading.visibility = View.GONE
                        if(list.isEmpty()) {
                            Snackbar.make(b.root,"Seems there are no blogs here.",Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                                fetchData()
                            }.show()
                        }
                        return@observe
                    } else {
                        val oldSize = list.size
                        stopShimmer()
                        b.loading.visibility = View.GONE
                        b.blogRecycler.setPadding(0,0,0,0)
                        list.addAll(it.r.results)
                        Log.d("Category Activity",list.size.toString()+" "+it.r.results.size.toString())
                        adapter.notifyItemRangeInserted(oldSize,it.r.results.size)
                    }
                }
                is Event.Error -> {
                    isLoading=false
                    b.loading.visibility=View.GONE
                    b.blogRecycler.setPadding(0,0,0,0)
                    Snackbar.make(b.root,it.msg,Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                        fetchData()
                    }.show()
                }
            }
        }
    }

    private fun setRecycler() {

        Glide.with(this).load(R.raw.loading_gif).into(b.loading)
        adapter = BlogAdapter(list)

        b.blogRecycler.layoutManager = LinearLayoutManager(this)
        b.blogRecycler.adapter = adapter
        b.blogRecycler.addOnScrollListener(scrollListener)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isScrolling

            if (shouldPaginate) {
                fetchData()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun startShimmer() {
        b.data.visibility = View.GONE
        b.skeletonLayout.visibility = View.VISIBLE
        b.skeletonLayout.startShimmer()
    }

    private fun stopShimmer() {
        b.skeletonLayout.stopShimmer()
        b.skeletonLayout.visibility = View.GONE
        b.data.visibility = View.VISIBLE
    }

    private fun setAppBar() {
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.customView?.apply {
            findViewById<TextView>(R.id.app_bar_text).text = category
            findViewById<CircleImageView>(R.id.user_image).setImageResource(R.color.white_ex)
        }
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.white_ex))
    }
}