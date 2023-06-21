package com.miniclip.footb.viewmodels

import android.net.Uri
import android.webkit.ValueCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniclip.footb.internet_view_preference.custom_view.CustomInternetChromeClient
import com.miniclip.footb.services.local_cache.MyDataStoreImpl
import com.miniclip.footb.services.network.data.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun setMyChrome(chromeCallback: (ValueCallback<Array<Uri>>?) -> Unit) {
        CustomInternetChromeClient(chromeCallback)
    }

}