package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.example.ingsoftappmobiles.models.CollectorDetail
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorDetailRepository (private val application: Application){
    suspend fun refreshData(collectorId: Int): CollectorDetail {
        return CollectorServiceAdapter.getInstance(application).getCollector(collectorId)
    }
}