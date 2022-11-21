package com.example.ingsoftappmobiles.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumItemBinding
import com.example.ingsoftappmobiles.models.Album
//import com.example.ingsoftappmobiles.ui.albums.AlbumsFragmentDirections

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>(){

    var albums :List<Album> = emptyList()
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
            //albums[position].releaseYear = albums[position].releaseDate.substring(0..3)
            //albums[position].excerpt = albums[position].description.substring(0..56) + "..."

            it.album = albums[position]
            albums[position].cover.let { urlImagen ->
                val imgUri = urlImagen.toUri().buildUpon().scheme("https").build()
                it.imageCover.load(imgUri)
            }

        }
        /*holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumDetailFragment(albums[position].albumId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }*/
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
    }
}