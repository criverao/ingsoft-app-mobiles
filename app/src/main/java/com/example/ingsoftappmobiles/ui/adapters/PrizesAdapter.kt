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
import com.example.ingsoftappmobiles.databinding.PrizeItemBinding
import com.example.ingsoftappmobiles.models.Prize

//import com.example.ingsoftappmobiles.ui.prizes.PrizesFragmentDirections


class PrizesAdapter : RecyclerView.Adapter<PrizesAdapter.PrizesViewHolder>(){

    var prizes :List<Prize> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrizesViewHolder {
        val withDataBinding: PrizeItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PrizesViewHolder.LAYOUT,
            parent,
            false)
        return PrizesViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PrizesViewHolder, position: Int) {
        holder.viewDataBinding.also {
            //prizes[position].releaseYear = prizes[position].releaseDate.substring(0..3)
            //prizes[position].excerpt = prizes[position].description.substring(0..56) + "..."

            it.prize = prizes[position]
        }
        /*holder.viewDataBinding.root.setOnClickListener {
            val action = PrizesFragmentDirections.actionPrizesFragmentToPrizeDetailFragment(prizes[position].prizeId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return prizes.size
        //return 1
    }

    class PrizesViewHolder(val viewDataBinding: PrizeItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.prize_item
        }
    }
}