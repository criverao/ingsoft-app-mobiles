package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumDetailBinding
import com.example.ingsoftappmobiles.models.AlbumDetail

class AlbumDetailAdapter : RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>(){

    var album : AlbumDetail? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val withDataBinding: AlbumDetailBinding? = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailViewHolder.LAYOUT,
            parent,
            false)
        return AlbumDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {

        holder.viewDataBinding?.also {
            it.albumDetail = album
            album?.releaseDate = album?.releaseDate?.substring(0..3).toString()

            val layoutManagerTracks = LinearLayoutManager(
                it.albumTrackRecyclerView.context,
                LinearLayoutManager.VERTICAL, false
            )
            layoutManagerTracks.initialPrefetchItemCount = album?.tracks?.count() ?: 0

            val tracksAdapter = TracksAdapter()
            tracksAdapter.tracks = album?.tracks ?: tracksAdapter.tracks
            it.albumTrackRecyclerView.layoutManager = layoutManagerTracks
            it.albumTrackRecyclerView.adapter = tracksAdapter
            it.albumTrackRecyclerView.setRecycledViewPool(viewPool)

            val layoutManagerComments = LinearLayoutManager(
                it.albumTrackRecyclerView.context,
                LinearLayoutManager.VERTICAL, false
            )
            layoutManagerComments.initialPrefetchItemCount = album?.comments?.count() ?: 0

            val commentsAdapter = CommentsAdapter()
            commentsAdapter.comments = album?.comments ?: commentsAdapter.comments
            it.albumCommentRecyclerView.layoutManager = layoutManagerComments
            it.albumCommentRecyclerView.adapter = commentsAdapter
            it.albumCommentRecyclerView.setRecycledViewPool(viewPool)

        }
        album?.let { holder.bind(it) }
    }

    class AlbumDetailViewHolder(val viewDataBinding: AlbumDetailBinding?) :
        RecyclerView.ViewHolder(viewDataBinding?.root!!) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail
        }

        fun bind(album: AlbumDetail) {
            viewDataBinding?.let {
                Glide.with(itemView)
                    .load(album.cover.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image))
                    .into(it.imageCover)
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}