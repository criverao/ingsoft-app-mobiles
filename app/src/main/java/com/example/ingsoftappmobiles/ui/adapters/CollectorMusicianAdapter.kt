package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.CollectorMusicianItemBinding
import com.example.ingsoftappmobiles.models.Musician

class CollectorMusicianAdapter : RecyclerView.Adapter<CollectorMusicianAdapter.CollectorMusicianViewHolder>(){

    var musicians :List<Musician> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorMusicianViewHolder {
        val withDataBinding: CollectorMusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorMusicianViewHolder.LAYOUT,
            parent,
            false)
        return CollectorMusicianViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorMusicianViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musician = musicians[position]

            musicians[position].description = musicians[position].description.substring(0..60)

            musicians[position].image.let { urlImagen ->
                val imgUri = urlImagen.toUri().buildUpon().scheme("https").build()
                it.musicianImage.load(imgUri)
            }
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }

    class CollectorMusicianViewHolder(val viewDataBinding: CollectorMusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_musician_item
        }
    }
}