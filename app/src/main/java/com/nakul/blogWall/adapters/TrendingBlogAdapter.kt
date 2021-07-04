package com.nakul.blogWall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nakul.blogWall.activities.BlogActivity
import com.nakul.blogWall.databinding.ItemTrendingBlogsBinding
import com.nakul.blogWall.models.blog.Blog


class TrendingBlogAdapter(private val list: ArrayList<Blog>) :
    RecyclerView.Adapter<TrendingBlogAdapter.ViewHolder>() {

    class ViewHolder(_binding: ItemTrendingBlogsBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        var binding: ItemTrendingBlogsBinding = _binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemTrendingBlogsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list.isNotEmpty()) {
            val b = holder.binding
            Glide.with(holder.itemView.context)
                .load(list[position].image)
                .into(b.image)
            b.heading.text = list[position].title
            b.root.setOnClickListener {
                BlogActivity.start(it.context, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty())
            1
        else
            list.size
    }
}