package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.models.Collector
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorsRepository(private val application: Application) {
    suspend fun refreshData(): List<Collector> {
        return CollectorServiceAdapter.getInstance(application).getCollectors()
    }
}