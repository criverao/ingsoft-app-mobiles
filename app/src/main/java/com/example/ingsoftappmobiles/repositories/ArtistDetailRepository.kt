package com.example.ingsoftappmobiles.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ingsoftappmobiles.models.AlbumDetail
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.models.Prize
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter

class ArtistDetailRepository (private val application: Application, private val prizeRepository: PrizeRepository){
    suspend fun refreshData(artistId: Int, tipo: String): Artist {
        var artist:Artist
        if (tipo.equals("Banda")){
            artist = ArtistServiceAdapter.getInstance(application).getBand(artistId)
        }
        else {
            artist = ArtistServiceAdapter.getInstance(application).getMusician(artistId)
        }

        var prize2: Prize
        for (prize in artist.prizes){
            prize2 = prizeRepository.refreshData(prize.id)
            prize2.description.also { prize.description = it }
            prize2.name.also { prize.name = it }
            prize2.organization.also { prize.organization = it }
        }
        return artist
    }
}