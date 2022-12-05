package com.example.ingsoftappmobiles.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.database.dao.AlbumsDao
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter

class AlbumsRepository (val application: Application, private val albumsDao: AlbumsDao){
    suspend fun refreshData(): List<Album> {
        var cached = albumsDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            Log.d("Cache decision", "get from network")
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Log.d("Cache decision", "get Room")
                emptyList()
            } else {
                Log.d("Cache decision", "get from network")
                cached = AlbumServiceAdapter.getInstance(application).getAlbums()
                insertAlbums(cached)
                cached
            }
        } else {
            Log.d("Cache decision", "get from caché")
            cached
        }
    }

    private suspend fun insertAlbums(albums: List<Album>){
        for (album in albums) {
            albumsDao.insert(album)
        }
    }

    fun createAlbum(album:Album, onComplete:(resp:Album)->Unit, onError: (error:VolleyError)->Unit){
        AlbumServiceAdapter.getInstance(application).postAlbum(album, onComplete, onError)
    }
}