package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter

class AlbumsRepository (private val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        // Determinar la fuente de datos que se va a utilizar.
        AlbumServiceAdapter.getInstance(application).getAlbums({
            //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }

    fun createAlbum(album:Album, onComplete:(resp:Album)->Unit, onError: (error:VolleyError)->Unit){
        AlbumServiceAdapter.getInstance(application).postAlbum(album, onComplete, onError)
    }
}