package com.example.ingsoftappmobiles.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter
import com.example.ingsoftappmobiles.network.CacheManager

class AlbumsRepository (private val application: Application){
    suspend fun refreshData(): List<Album> {

        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums()
        return if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            val albums = AlbumServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(albums)
            albums
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

    fun createAlbum(album:Album, onComplete:(resp:Album)->Unit, onError: (error:VolleyError)->Unit){
        AlbumServiceAdapter.getInstance(application).postAlbum(album, onComplete, onError)
    }
}