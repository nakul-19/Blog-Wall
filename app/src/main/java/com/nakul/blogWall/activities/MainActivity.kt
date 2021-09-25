package com.nakul.blogWall.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        setClick()
        setObserver()
        fetchData()
    }

    private fun setClick() {
        binding.newBlog.setOnClickListener {
            startActivity(Intent(this,NewBlogActivity::class.java))
        }
    }

    private fun setObserver() {
        viewModel.trending.observe(this) {

            when (it) {
                is Event.Error -> {
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry") {
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
        viewModel.setImage() { url: String? ->
            Glide.with(this)
                .load(url)
                .into(binding.bar.userImage)
        }
        binding.bar.spinner.apply {
            val optionList= arrayListOf(viewModel.getName(),"Logout")
            adapter =
                ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    optionList
                )

            this.post{
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?, position: Int, id: Long
                    ) {
                        when(position) {
                            0-> { }
                            1-> {
                                viewModel.logout()
                                this@MainActivity.startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                                this@MainActivity.finish()
                            }
                        }
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>?) {}
                }
            }

        }
        binding.bar.userImage.setOnClickListener {
            binding.bar.spinner.performClick()
        }
    }
}