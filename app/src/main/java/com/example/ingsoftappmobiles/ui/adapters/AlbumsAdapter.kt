package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumItemBinding
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.ui.albums.AlbumFragmentDirections

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>(){

    var albums :List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumsViewHolder.LAYOUT,
            parent,
            false)
        return AlbumsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]

            albums[position].releaseYear = albums[position].releaseDate.substring(0..3)

            val descriptionLength = albums[position].description.length
            val maxLength = 57

            if (descriptionLength > maxLength) {
                albums[position].excerpt = albums[position].description.substring(0..maxLength) + "..."
            } else {
                albums[position].excerpt = albums[position].description
            }

        }
        holder.bind(albums[position])
        holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumFragmentDirections.showAlbumDetailFragment(albums[position].albumId)
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumsViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }

        fun bind(album: Album) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(viewDataBinding.imageCover)
        }

    }
}