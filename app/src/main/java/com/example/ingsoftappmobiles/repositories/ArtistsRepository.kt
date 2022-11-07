package com.example.ingsoftappmobiles.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.models.Band
import com.example.ingsoftappmobiles.models.Musician
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class ArtistsRepository (val application: Application) {
    fun refreshData(callbackMusician: (List<Artist>)->Unit, callbackBand: (List<Artist>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        ArtistServiceAdapter.getInstance(application).getMusiciansOnArtist({
            //Guardar los artistes de la variable it en un almacén de datos local para uso futuro
            callbackMusician(it)
        },
            onError
        )

        ArtistServiceAdapter.getInstance(application).getBandsOnArtist({
            //Guardar los artistes de la variable it en un almacén de datos local para uso futuro
            callbackBand(it)
        },
            onError
        )

    }
}