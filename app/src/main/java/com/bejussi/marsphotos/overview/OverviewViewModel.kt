package com.bejussi.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bejussi.marsphotos.network.MarsApi
import com.bejussi.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

class OverviewViewModel: ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    private val _photos = MutableLiveData<MarsPhoto>()
    val photos: LiveData<MarsPhoto> = _photos

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        try {
            viewModelScope.launch {
                _photos.value = MarsApi.retrofitService.getPhotos()[0]
                _status.value = "Success: ${_photos.value!!.imgSrcUrl} Mars photos retrieved"
            }
        } catch (e: Exception) {
            _status.value = "Failure: ${e.message}"
        }
    }
}