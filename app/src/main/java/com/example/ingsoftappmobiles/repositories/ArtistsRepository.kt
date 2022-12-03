package com.example.ingsoftappmobiles.repositories

import android.app.Application
import android.util.Log
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.network.CacheManager
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class ArtistsRepository (val application: Application) {
    suspend fun refreshData() : List<Artist> {

        val potentialResp = CacheManager.getInstance(application.applicationContext).getArtists()
        return if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            val musicians: List<Artist> = ArtistServiceAdapter.getInstance(application).getMusiciansOnArtist()
            val bands = ArtistServiceAdapter.getInstance(application).getBandsOnArtist()
            val artists = musicians + bands
            CacheManager.getInstance(application.applicationContext).addArtists(artists)
            artists
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }

    }
}