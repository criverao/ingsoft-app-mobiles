package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.example.ingsoftappmobiles.models.Band
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class BandRepository (val application: Application) {
    suspend fun refreshData(): List<Band> {
        return ArtistServiceAdapter.getInstance(application).getBands()
    }
}