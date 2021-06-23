package com.nakul.blogWall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nakul.blogWall.databinding.ItemBlogBinding
import com.nakul.blogWall.models.Blog

class BlogAdapter(val list: ArrayList<Blog>) : RecyclerView.Adapter<BlogAdapter.VH>() {
    class VH(val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemBlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(h: VH, pos: Int) {
        h.binding.blogAuthor.text = list[pos].author
        h.binding.blogHeading.text = list[pos].heading
        h.binding.blogDetails.text = list[pos].timeToRead
        Glide.with(h.itemView.context).load(list[pos].image).into(h.binding.blogImage)
    }

    override fun getItemCount(): Int = list.size
}