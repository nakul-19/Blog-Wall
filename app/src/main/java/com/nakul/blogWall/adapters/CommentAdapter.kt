package com.nakul.blogWall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nakul.blogWall.databinding.ItemCommentBinding
import com.nakul.blogWall.models.like_and_comment.Comment
import com.nakul.blogWall.utils.UtilConstants
import com.nakul.blogWall.utils.UtilFunctions.format
import com.nakul.blogWall.utils.UtilFunctions.toDate

class CommentAdapter(val list: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.VH>() {

    class VH(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val b = holder.binding
        b.date.text = list[pos].timestamp.toDate().format()
        b.userName.text = list[pos].owner.name
        b.comment.text = list[pos].content
        if (list[pos].owner.profile_pic.isNotBlank())
            Glide.with(b.root).load(
                if (list[pos].owner.profile_pic[0] == '/') UtilConstants.baseurl + list[pos].owner.profile_pic
                else list[pos].owner.profile_pic
            ).into(b.userImage)
    }

    override fun getItemCount(): Int = list.size

}