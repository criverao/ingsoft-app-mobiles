package com.example.ingsoftappmobiles.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ingsoftappmobiles.models.AlbumDetail
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.models.Prize
import com.example.ingsoftappmobiles.network.AlbumServiceAdapter
import com.example.ingsoftappmobiles.network.ArtistServiceAdapter
import com.example.ingsoftappmobiles.network.PrizeServiceAdapter

class PrizeRepository (private val application: Application){
    suspend fun refreshData(prizeId: Int): Prize {
            return PrizeServiceAdapter.getInstance(application).getPrize(prizeId)  }
}