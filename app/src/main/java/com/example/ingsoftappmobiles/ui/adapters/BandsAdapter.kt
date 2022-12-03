package com.example.ingsoftappmobiles.ui.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.BandItemBinding
import com.example.ingsoftappmobiles.models.Band

class BandsAdapter  : RecyclerView.Adapter<BandsAdapter.BandViewHolder>(){

    var bands :List<Band> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
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
        }

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


