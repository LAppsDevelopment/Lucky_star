package com.miniclip.footb.ui.intro_screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniclip.footb.ui.intro_screen.repository.IntroRepository
import com.miniclip.footb.ui.model.TrackingData
import com.miniclip.footb.ui.services.local_cache.MyDataStoreImpl
import com.miniclip.footb.ui.services.network.data.RemoteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repository: IntroRepository,
    private val dataStore: MyDataStoreImpl
) : ViewModel() {

    private val _finalLinkState = MutableStateFlow(RemoteResponse())
    val finalLinkState = _finalLinkState.asStateFlow()

    private val _savedUrlState = MutableStateFlow<String?>(null)
    val savedUrlState = _savedUrlState.asStateFlow()

    fun getRemoteData(dataToSend: TrackingData) = viewModelScope.launch(Dispatchers.IO) {
        val serverResponse = repository.getData(dataToSend)
        try {
            _finalLinkState.emit(serverResponse)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveUrlToDataStore(url: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveUrl(url)
        }
    }

    fun getUrlFromDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = dataStore.getUrl()
            _savedUrlState.emit(url)
        }
    }

}