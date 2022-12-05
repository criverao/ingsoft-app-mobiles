package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.example.ingsoftappmobiles.models.Prize
import com.example.ingsoftappmobiles.network.PrizeServiceAdapter

class PrizeRepository (private val application: Application){
    suspend fun refreshData(prizeId: Int): Prize {
            return PrizeServiceAdapter.getInstance(application).getPrize(prizeId)  }
}