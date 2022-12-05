package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.AlbumDetail
import com.example.ingsoftappmobiles.models.Track
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter

class AlbumDetailRepository (private val application: Application){
    suspend fun refreshData(albumId: Int): AlbumDetail {
        return AlbumServiceAdapter.getInstance(application).getAlbum(albumId)
    }

    fun createTrack(track: Track, onComplete:(resp: Track)->Unit, onError: (error: VolleyError)->Unit){
        AlbumServiceAdapter.getInstance(application).postTrack(track, onComplete, onError)
    }
}