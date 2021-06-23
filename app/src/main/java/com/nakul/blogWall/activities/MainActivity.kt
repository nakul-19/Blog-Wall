package com.nakul.blogWall.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.nakul.blogWall.R
import com.nakul.blogWall.adapters.CategoriesAdapter
import com.nakul.blogWall.adapters.TrendingBlogAdapter
import com.nakul.blogWall.databinding.ActivityMainBinding
import com.nakul.blogWall.utils.UtilFunctions

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAppBar()
        setCategories()
        setTrending()
    }

    private fun setTrending() {
        binding.trendingBlog.adapter = TrendingBlogAdapter(ArrayList())
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
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.white_ex))
        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.customView?.findViewById<TextView>(R.id.app_bar_text)?.text =
            UtilFunctions.getGreetings()
        Glide.with(this).load("https://mediaslide-europe.storage.googleapis.com/uno/pictures/2977/41110/profile-1540283773-5ac020b51f559faf895c30a9189b5b4d.jpg").into(supportActionBar!!.customView.findViewById(R.id.user_image))
    }
}