package com.nakul.blogWall.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nakul.blogWall.R
import com.nakul.blogWall.adapters.BlogAdapter
import com.nakul.blogWall.databinding.ActivityCategoryBinding
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
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(b.root)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        setAppBar()
        setRecycler()

    }

    private fun setRecycler() {
        b.blogRecycler.layoutManager = LinearLayoutManager(this)
        b.blogRecycler.adapter = BlogAdapter(ArrayList())
        val skeleton = b.skeletonLayout
        skeleton.startShimmer()
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