package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.example.ingsoftappmobiles.models.AlbumDetail
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter

class AlbumDetailRepository (private val application: Application){
    suspend fun refreshData(albumId: Int): AlbumDetail {
        return AlbumServiceAdapter.getInstance(application).getAlbum(albumId)
    }
}