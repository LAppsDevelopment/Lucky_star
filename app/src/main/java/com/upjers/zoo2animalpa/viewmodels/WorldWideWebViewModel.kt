package com.upjers.zoo2animalpa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upjers.zoo2animalpa.services.local_cache.MyDataStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorldWideWebViewModel @Inject constructor(
    private val dataStore: MyDataStoreImpl
) : ViewModel() {

    fun saveUrlToDataStore(url: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveUrl(url)
        }
    }

}