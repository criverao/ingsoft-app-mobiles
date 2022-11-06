package com.example.ingsoftappmobiles.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.ingsoftappmobiles.models.Band
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.repositories.BandRepository
import com.example.ingsoftappmobiles.repositories.ArtistsRepository

class ArtistViewModel(application: Application) :  AndroidViewModel(application) {

    private val artistsRepository = ArtistsRepository(application)

    private val _artists = MutableLiveData<List<Artist>>()

    val artists: LiveData<List<Artist>>
        get() = _artists

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        artistsRepository.refreshData({
            _artists.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },
            {
            _artists.value = _artists.value.orEmpty() + it
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        }
        )

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}


