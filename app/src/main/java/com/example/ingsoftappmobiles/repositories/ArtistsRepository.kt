package com.example.ingsoftappmobiles.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.ingsoftappmobiles.database.dao.AlbumsDao
import com.example.ingsoftappmobiles.database.dao.ArtistsDao
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter
import com.example.ingsoftappmobiles.network.CacheManager
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class ArtistsRepository (val application: Application, private val artistsDao: ArtistsDao) {
    suspend fun refreshData() : List<Artist> {

        var cached = artistsDao.getArtists()
        return if(cached.isNullOrEmpty()){
            Log.d("Cache decision", "get from network")
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Log.d("Cache decision", "get Room")
                emptyList()
            } else {
                Log.d("Cache decision", "get from network")
                cached =ArtistServiceAdapter.getInstance(application).getMusiciansArtists()
                insertArtists(cached)
                cached = ArtistServiceAdapter.getInstance(application).getBandsArtists()
                insertArtists(cached)
                cached
            }
        } else {
            Log.d("Cache decision", "get from caché")
            cached
        }

    }

    private suspend fun insertArtists(artists: List<Artist>){
        for (artist in artists) {
            artistsDao.insert(artist)
        }
    }
}