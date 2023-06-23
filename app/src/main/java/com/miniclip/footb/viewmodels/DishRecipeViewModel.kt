package com.miniclip.footb.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniclip.footb.model.api.DishApiResponse
import com.miniclip.footb.services.image_api.DishApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishRecipeViewModel @Inject constructor(
    private val dishApiImpl: DishApiImpl
) : ViewModel() {

    private val _dishImageFlow = MutableSharedFlow<DishApiResponse?>(1)
    val dishImageFlow = _dishImageFlow.asSharedFlow()

    private val _chatResponse = MutableSharedFlow<String?>(1)
    val chatResponse = _chatResponse.asSharedFlow()

    fun setupFragmentViews(userQuery: String) {
        Log.e(TAG, "init started: ")
        viewModelScope.launch(Dispatchers.IO) {
            _dishImageFlow.emit((dishApiImpl.requestDishPhoto(userQuery))
            )
        }

        pushChatResponse()
    }

    private fun pushChatResponse() {
        //todo implement chat response

        viewModelScope.launch(Dispatchers.IO) {
            _chatResponse.emit("chat response ;)")
        }
    }

    companion object {
        private const val TAG = "DishRecipeViewModel"
    }
}