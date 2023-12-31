package com.miniclip.footb.viewmodels

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

    fun setupAppBarImage(userQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _dishImageFlow.emit((dishApiImpl.requestDishPhoto(userQuery)))
        }
    }

}