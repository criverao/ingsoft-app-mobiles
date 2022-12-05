package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumTrackItemBinding
import com.example.ingsoftappmobiles.models.Track

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>(){

    var tracks :List<Track> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val withDataBinding: AlbumTrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            TracksViewHolder.LAYOUT,
            parent,
            false)
        return TracksViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    class TracksViewHolder(val viewDataBinding: AlbumTrackItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_track_item
        }
    }
}