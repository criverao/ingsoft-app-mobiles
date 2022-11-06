package com.example.ingsoftappmobiles.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.ingsoftappmobiles.models.Band
import com.example.ingsoftappmobiles.models.Musician
import com.example.ingsoftappmobiles.repositories.BandRepository
import com.example.ingsoftappmobiles.repositories.MusicianRepository

class ArtistViewModel(application: Application) :  AndroidViewModel(application) {

    private val musiciansRepository = MusicianRepository(application)
    private val bandsRepository = BandRepository(application)

    private val _musicians = MutableLiveData<List<Musician>>()
    private val _bands = MutableLiveData<List<Band>>()

    val musicians: LiveData<List<Musician>>
        get() = _musicians

    val bands: LiveData<List<Band>>
        get() = _bands

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
        musiciansRepository.refreshData({
            _musicians.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })

        bandsRepository.refreshData({
            _bands.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })

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



