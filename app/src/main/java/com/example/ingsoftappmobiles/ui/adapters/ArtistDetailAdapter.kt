package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.ArtistDetailBinding
import com.example.ingsoftappmobiles.models.ArtistDetail

class ArtistDetailAdapter : RecyclerView.Adapter<ArtistDetailAdapter.ArtistDetailViewHolder>(){

    var artist : ArtistDetail? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistDetailViewHolder {
        val withDataBinding: ArtistDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistDetailViewHolder.LAYOUT,
            parent,
            false)
        return ArtistDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistDetailViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.artist = artist

            val layoutManager = LinearLayoutManager(it.artistDetailAlbumsRv.context,
                LinearLayoutManager.VERTICAL, false)
            layoutManager.initialPrefetchItemCount = artist?.albums?.count() ?: 0

            val albumsAdapter = AlbumsAdapter()
            albumsAdapter.albums = artist?.albums ?: albumsAdapter.albums
            //albumsAdapter.albums = albums
            it.artistDetailAlbumsRv.layoutManager = layoutManager
            it.artistDetailAlbumsRv.adapter = albumsAdapter
            it.artistDetailAlbumsRv.setRecycledViewPool(viewPool)

            val layoutManager2 = LinearLayoutManager(it.artistDetailPrizesRv.context,
                LinearLayoutManager.VERTICAL, false)
            layoutManager.initialPrefetchItemCount = artist?.prizes?.count() ?: 0

            val prizesAdapter = PrizesAdapter()
            prizesAdapter.prizes = artist?.prizes ?: prizesAdapter.prizes
            //albumsAdapter.albums = albums
            it.artistDetailPrizesRv.layoutManager = layoutManager2
            it.artistDetailPrizesRv.adapter = prizesAdapter
            it.artistDetailPrizesRv.setRecycledViewPool(viewPool)

        }
        artist?.let { holder.bind(it) }

    }

    class ArtistDetailViewHolder(val viewDataBinding: ArtistDetailBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_detail
        }

        fun bind(artist: ArtistDetail) {
            Glide.with(itemView)
                .load(artist.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(viewDataBinding.imageCoverDetail)
        }
    }

    override fun getItemCount(): Int {
        return 1
    }


}