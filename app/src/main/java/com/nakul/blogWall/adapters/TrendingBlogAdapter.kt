package com.nakul.blogWall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nakul.blogWall.R
import com.nakul.blogWall.activities.BlogActivity
import com.nakul.blogWall.databinding.ItemTrendingBlogsBinding
import com.nakul.blogWall.models.Blog


class TrendingBlogAdapter(val list: ArrayList<Blog>) :
    RecyclerView.Adapter<TrendingBlogAdapter.ViewHolder>() {

    class ViewHolder(private val _binding: ItemTrendingBlogsBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        var binding: ItemTrendingBlogsBinding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemTrendingBlogsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val b = holder.binding
        b.image.setImageResource(R.color.grey)
        Glide.with(holder.itemView.context)
            .load("https://www.training.com.au/wp-content/uploads/career-in-technology-feature.png")
            .into(b.image)
        b.heading.text = "Sample blog heading"
        b.root.setOnClickListener {
            BlogActivity.start(it.context,Blog("Sample Heading","","https://t4.ftcdn.net/jpg/02/86/02/67/360_F_286026740_xWkobcEk5g38qrH7cpfeImAnlUUSIrc5.jpg","Leo James","https://i.pinimg.com/originals/96/2f/27/962f27bb83a6e0b47fdc6d28727986c3.jpg","2 min"))
        }
    }

    override fun getItemCount(): Int = 5
}