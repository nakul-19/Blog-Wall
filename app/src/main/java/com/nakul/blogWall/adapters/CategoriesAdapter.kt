package com.nakul.blogWall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nakul.blogWall.activities.CategoryActivity
import com.nakul.blogWall.databinding.ItemCategoriesBinding

class CategoriesAdapter(val list: ArrayList<Pair<String, Int>>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    class ViewHolder(private val b: ItemCategoriesBinding) : RecyclerView.ViewHolder(b.root) {
        val binding = b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val b=holder.binding
        b.category.text = list[position].first
        b.image.setImageResource(list[position].second)
        holder.itemView.setOnClickListener {
            CategoryActivity.start(it.context,list[position].first)
        }
    }

    override fun getItemCount(): Int = list.size
}