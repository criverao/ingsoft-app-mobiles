package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class ArtistsRepository (val application: Application) {
    suspend fun refreshData() : List<Artist> {
        var artists: List<Artist> = ArtistServiceAdapter.getInstance(application).getMusiciansOnArtist()

        val bands = ArtistServiceAdapter.getInstance(application).getBandsOnArtist()

        return artists + bands

    }
}