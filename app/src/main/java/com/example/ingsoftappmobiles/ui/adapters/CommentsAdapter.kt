package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumCommentItemBinding
import com.example.ingsoftappmobiles.models.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>(){

    var comments :List<Comment> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val withDataBinding: AlbumCommentItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CommentsViewHolder.LAYOUT,
            parent,
            false)
        return CommentsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comment = comments[position]
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class CommentsViewHolder(val viewDataBinding: AlbumCommentItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_comment_item
        }
    }
}