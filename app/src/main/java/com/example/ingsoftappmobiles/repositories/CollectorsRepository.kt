package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Collector
import com.example.ingsoftappmobiles.network.CollectorServiceAdapter

class CollectorsRepository(private val application: Application) {
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        CollectorServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
            onError
        )
    }
}