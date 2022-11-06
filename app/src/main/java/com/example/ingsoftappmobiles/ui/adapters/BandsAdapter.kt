package com.example.ingsoftappmobiles.ui.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.BandItemBinding

import com.example.ingsoftappmobiles.models.Band
import coil.load

class BandsAdapter  : RecyclerView.Adapter<BandsAdapter.BandViewHolder>(){

    var bands :List<Band> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandViewHolder {
        val withDataBinding: BandItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            BandViewHolder.LAYOUT,
            parent,
            false)
        return BandViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: BandViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.band = bands[position]
            bands[position].image?.let { urlImagen ->
                val imgUri = urlImagen.toUri().buildUpon().scheme("https").build()
                it.imageView.load(imgUri)
            }
        }

/*        holder.viewDataBinding.root.setOnClickListener {
            val action = BandFragmentDirections.actionBandFragmentToCommentFragment(bands[position].bandId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return bands.size
    }


    class BandViewHolder(val viewDataBinding: BandItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.band_item
        }
    }


}


