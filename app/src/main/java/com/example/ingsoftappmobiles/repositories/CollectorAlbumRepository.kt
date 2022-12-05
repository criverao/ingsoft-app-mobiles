package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.example.ingsoftappmobiles.models.CollectorAlbum
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorAlbumRepository (private val application: Application){
    suspend fun refreshData(collectorId: Int, albumId:Int): CollectorAlbum {
        return CollectorServiceAdapter.getInstance(application).getCollectorAlbum(collectorId, albumId)
    }
}