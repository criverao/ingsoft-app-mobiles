package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.CollectorAlbumItemBinding
import com.example.ingsoftappmobiles.models.CollectorAlbum


class CollectorAlbumAdapter : RecyclerView.Adapter<CollectorAlbumAdapter.CollectorAlbumViewHolder>(){

    var albums :List<CollectorAlbum> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumViewHolder {
        val withDataBinding: CollectorAlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorAlbumViewHolder.LAYOUT,
            parent,
            false)
        return CollectorAlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorAlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collectorAlbum = albums[position]

            albums[position].albumGenre = "Género:  " + albums[position].albumGenre
            albums[position].price = "Precio: $ " + albums[position].price
            albums[position].status = "Estado: " + albums[position].status

        }
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class CollectorAlbumViewHolder(val viewDataBinding: CollectorAlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_album_item
        }

        fun bind(album: CollectorAlbum) {
            Glide.with(itemView)
                .load(album.albumCover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(viewDataBinding.collectorAlbumCover)
        }
    }
}