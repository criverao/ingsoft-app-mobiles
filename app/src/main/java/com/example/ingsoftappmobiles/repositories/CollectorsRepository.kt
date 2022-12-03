package com.example.ingsoftappmobiles.repositories

import android.app.Application
import android.util.Log
import com.example.ingsoftappmobiles.models.Collector
import com.example.ingsoftappmobiles.network.CacheManager
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorsRepository(private val application: Application) {
    suspend fun refreshData(): List<Collector> {

        val potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors()
        return if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            val collectors = CollectorServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(collectors)
            collectors
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}