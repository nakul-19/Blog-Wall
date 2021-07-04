package com.nakul.blogWall.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.R
import com.nakul.blogWall.adapters.CategoriesAdapter
import com.nakul.blogWall.adapters.TrendingBlogAdapter
import com.nakul.blogWall.databinding.ActivityMainBinding
import com.nakul.blogWall.models.blog.Blog
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.utils.UtilFunctions
import com.nakul.blogWall.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    val trendingList = ArrayList<Blog>()
    lateinit var adapter: TrendingBlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAppBar()
        setCategories()
        setTrendingUI()
        setObserver()
        fetchData()
    }

    private fun setObserver() {
        viewModel.trending.observe(this) {

            when (it) {
                is Event.Error -> {
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                        fetchData()
                    }.show()
                }
                is Event.Success -> {
                    trendingList.clear()
                    trendingList.addAll(it.r)
                    adapter.notifyDataSetChanged()
                }
                else -> {
                }
            }
        }
    }

    private fun fetchData() {
        viewModel.getTrendingBlogs()
    }

    private fun setTrendingUI() {
        adapter = TrendingBlogAdapter(trendingList)
        binding.trendingBlog.adapter = adapter
        binding.trendingDots.setViewPager2(binding.trendingBlog)
    }

    private fun setCategories() {
        binding.categoryRecycler.layoutManager = GridLayoutManager(this, 2)
        val list = ArrayList<Pair<String, Int>>()

        list.add(Pair("Science", R.drawable.science_bg))
        list.add(Pair("Technology", R.drawable.technology_bg))
        list.add(Pair("Global Affairs", R.drawable.international_bg))
        list.add(Pair("Health Care", R.drawable.health_bg))
        list.add(Pair("Entertainment", R.drawable.entertainment_bg))
        list.add(Pair("Sports", R.drawable.sports_bg))

        binding.categoryRecycler.adapter = CategoriesAdapter(list)
    }

    private fun setAppBar() {
        binding.bar.appBarText.text =
            UtilFunctions.getGreetings()
        Glide.with(this)
            .load("https://mediaslide-europe.storage.googleapis.com/uno/pictures/2977/41110/profile-1540283773-5ac020b51f559faf895c30a9189b5b4d.jpg")
            .into(binding.bar.userImage)
    }
}