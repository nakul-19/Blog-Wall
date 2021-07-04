package com.nakul.blogWall.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.R
import com.nakul.blogWall.adapters.BlogAdapter
import com.nakul.blogWall.adapters.PaginationScroller
import com.nakul.blogWall.databinding.ActivityCategoryBinding
import com.nakul.blogWall.models.blog.Blog
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.viewmodels.CategoryViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.delay

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
        viewModel.getList(list.size, category)
    }

    private fun setObserver() {
        viewModel.blogListResponse.observe(this) {
            when (it) {
                is Event.Loading -> {
                    if (list.isEmpty())
                        startShimmer()
                }
                is Event.Success -> {
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
//                        val oldSize = list.size
                        stopShimmer()
                        list.addAll(it.r.results)
                        Log.d("Category Activity",list.size.toString()+" "+it.r.results.size.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
                is Event.Error -> {
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

        val layoutManager = LinearLayoutManager(this)
        b.blogRecycler.layoutManager = layoutManager
        b.blogRecycler.adapter = adapter
        b.blogRecycler.addOnScrollListener(object : PaginationScroller(layoutManager) {
            override fun isLast(): Boolean {
                return allFetched
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMore() {
                fetchData()
            }

        })
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