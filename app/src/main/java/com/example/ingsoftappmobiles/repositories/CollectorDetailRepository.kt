package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.example.ingsoftappmobiles.models.CollectorAlbum
import com.example.ingsoftappmobiles.models.CollectorDetail
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorDetailRepository (private val application: Application, private val collectorAlbumRepository: CollectorAlbumRepository){
    suspend fun refreshData(collectorId: Int): CollectorDetail {
        val collectorDetail: CollectorDetail = CollectorServiceAdapter.getInstance(application).getCollector(collectorId)

        var collectorAlbums: CollectorAlbum
        for (collectorAlbum in collectorDetail.albums){
            collectorAlbums = collectorAlbumRepository.refreshData(collectorId, collectorAlbum.collectorAlbumId)
            collectorAlbums.albumName.also { collectorAlbum.albumName = it }
            collectorAlbums.albumGenre.also { collectorAlbum.albumGenre = it }
            collectorAlbums.albumCover.also { collectorAlbum.albumCover = it }

        }

        return collectorDetail
    }
}