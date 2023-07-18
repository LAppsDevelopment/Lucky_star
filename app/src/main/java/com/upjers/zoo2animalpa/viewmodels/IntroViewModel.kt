package com.upjers.zoo2animalpa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upjers.zoo2animalpa.model.TrackingData
import com.upjers.zoo2animalpa.services.local_cache.MyDataStoreImpl
import com.upjers.zoo2animalpa.services.network.data.ResponseData
import com.upjers.zoo2animalpa.ui.intro_screen.repository.IntroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repository: IntroRepository,
    private val dataStore: MyDataStoreImpl
) : ViewModel() {

    private val _finalLinkState = MutableSharedFlow<ResponseData>(1)
    val finalLinkState = _finalLinkState.asSharedFlow()

    private val _savedUrlState = MutableSharedFlow<String>()
    val savedUrlState = _savedUrlState.asSharedFlow()

    fun getRemoteData(dataToSend: TrackingData) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val serverResponse = repository.getData(dataToSend)

            _finalLinkState.emit(serverResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            _finalLinkState.emit(ResponseData(null, null))
        }
    }

    fun getUrlFromDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = dataStore.getUrl()
            _savedUrlState.emit(url.toString())
        }
    }
}