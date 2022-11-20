package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter

class AlbumsRepository (private val application: Application){
    suspend fun refreshData(): List<Album> {
        // Determinar la fuente de datos que se va a utilizar.
        // Si es necesario consultar la red, ejecutar el siguiente código
        return AlbumServiceAdapter.getInstance(application).getAlbums()
    }
}