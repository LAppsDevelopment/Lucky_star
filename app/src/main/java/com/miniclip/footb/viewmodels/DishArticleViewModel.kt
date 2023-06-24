package com.miniclip.footb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.miniclip.footb.model.api.DishApiResponse
import com.miniclip.footb.services.image_api.DishApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DishArticleViewModel @Inject constructor(
    private val dishApiImpl: DishApiImpl
) : ViewModel() {
    private val _dishImagesFlow = MutableSharedFlow<List<SlideModel>>(1)
    val dishImagesFlow = _dishImagesFlow.asSharedFlow()

    fun getImageList(articleName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var dishResponse: DishApiResponse?

            withContext(Dispatchers.Default) {
                dishResponse = dishApiImpl.requestDishPhoto(articleName, 5)
            }

            val convertedMap = mutableListOf<SlideModel>()

            dishResponse?.let {
                it.photos.forEach { photo ->
                    convertedMap.add(SlideModel(imageUrl = photo.src.landscape, title = photo.photographer, ScaleTypes.CENTER_CROP))
                }
            }

            _dishImagesFlow.emit(convertedMap)
        }
    }
}