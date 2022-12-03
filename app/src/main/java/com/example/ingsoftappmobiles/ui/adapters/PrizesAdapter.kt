package com.example.ingsoftappmobiles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.PrizeItemBinding
import com.example.ingsoftappmobiles.models.Prize


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
            it.prize = prizes[position]
        }
    }

    override fun getItemCount(): Int {
        return prizes.size
    }

    class PrizesViewHolder(val viewDataBinding: PrizeItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.prize_item
        }
    }
}