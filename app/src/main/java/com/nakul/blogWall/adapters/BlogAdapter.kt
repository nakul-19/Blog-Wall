package com.nakul.blogWall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nakul.blogWall.activities.BlogActivity
import com.nakul.blogWall.databinding.ItemBlogBinding
import com.nakul.blogWall.models.blog.Blog
import com.nakul.blogWall.utils.UtilFunctions.format
import com.nakul.blogWall.utils.UtilFunctions.toDate

class BlogAdapter(val list: ArrayList<Blog>) : RecyclerView.Adapter<BlogAdapter.VH>() {
    class VH(val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemBlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(h: VH, pos: Int) {
        h.binding.blogAuthor.text = list[pos].owner.name
        h.binding.blogHeading.text = list[pos].title
        h.binding.blogDetails.text = list[pos].publish.toDate().format()
        Glide.with(h.itemView.context).load(list[pos].image).into(h.binding.blogImage)

        h.binding.root.setOnClickListener {
            BlogActivity.start(it.context,list[pos])
        }
    }

    override fun getItemCount(): Int = list.size
}