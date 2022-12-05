package com.example.ingsoftappmobiles.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.ingsoftappmobiles.database.dao.CollectorsDao
import com.example.ingsoftappmobiles.models.Collector
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorsRepository(val application: Application, private val collectorsDao: CollectorsDao){
    suspend fun refreshData(): List<Collector> {
        var cached = collectorsDao.getCollectors()
        return if (cached.isNullOrEmpty()) {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                Log.d("Cache decision", "get Room")
                emptyList()
            } else {
                Log.d("Cache decision", "get from network")
                cached = CollectorServiceAdapter.getInstance(application).getCollectors()
                insertCollectors(cached)
                cached
            }
        } else {
            Log.d("Cache decision", "get from caché")
            cached
        }
    }

    private suspend fun insertCollectors(collectors: List<Collector>){
        for (collector in collectors) {
            collectorsDao.insert(collector)
        }
    }
}