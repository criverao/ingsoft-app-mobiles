package com.example.ingsoftappmobiles.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.ingsoftappmobiles.models.ArtistDetail
import com.example.ingsoftappmobiles.repositories.ArtistDetailRepository
import com.example.ingsoftappmobiles.repositories.PrizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistDetailViewModel(application: Application, artistId: Int, tipo:String) : AndroidViewModel(application) {

    private val prizeRepository = PrizeRepository(application)
    private val artistDetailRepository = ArtistDetailRepository(application, prizeRepository)


    private val _artist = MutableLiveData<ArtistDetail>()

    val artist: LiveData<ArtistDetail>
        get() = _artist

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = artistId
    private val tipo2:String = tipo

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    val data = artistDetailRepository.refreshData(id,tipo2)
                    _artist.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val artistId: Int, val tipo: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app, artistId, tipo) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}