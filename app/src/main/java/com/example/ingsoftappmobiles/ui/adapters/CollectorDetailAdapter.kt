package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.CollectorDetailBinding
import com.example.ingsoftappmobiles.models.CollectorDetail

class CollectorDetailAdapter : RecyclerView.Adapter<CollectorDetailAdapter.CollectorDetailViewHolder>(){

    var collector : CollectorDetail? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorDetailViewHolder {
        val withDataBinding: CollectorDetailBinding? = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorDetailViewHolder.LAYOUT,
            parent,
            false)
        return CollectorDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorDetailViewHolder, position: Int) {

        holder.viewDataBinding?.also {
            it.collectorDetail = collector

            val layoutManagerMusicians = LinearLayoutManager(
                it.collectorMusicianRecyclerView.context,
                LinearLayoutManager.VERTICAL, false
            )
            layoutManagerMusicians.initialPrefetchItemCount = collector?.musicians?.count() ?: 0

            val musicianAdapter = CollectorMusicianAdapter()
            musicianAdapter.musicians = collector?.musicians ?: musicianAdapter.musicians

            it.collectorMusicianRecyclerView.layoutManager = layoutManagerMusicians
            it.collectorMusicianRecyclerView.adapter = musicianAdapter
            it.collectorMusicianRecyclerView.setRecycledViewPool(viewPool)
        }

    }

    class CollectorDetailViewHolder(val viewDataBinding: CollectorDetailBinding?) :
        RecyclerView.ViewHolder(viewDataBinding?.root!!) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_detail
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}