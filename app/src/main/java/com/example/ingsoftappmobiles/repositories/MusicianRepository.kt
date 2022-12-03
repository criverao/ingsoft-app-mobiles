package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Musician
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class MusicianRepository (val application: Application) {
    suspend fun refreshData(): List<Musician> {
        return ArtistServiceAdapter.getInstance(application).getMusicians()
    }
}