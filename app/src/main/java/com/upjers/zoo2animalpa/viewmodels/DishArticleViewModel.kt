package com.upjers.zoo2animalpa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.upjers.zoo2animalpa.model.api.DishApiResponse
import com.upjers.zoo2animalpa.model.open_ai_api.CompletionRequest
import com.upjers.zoo2animalpa.model.open_ai_api.Message
import com.upjers.zoo2animalpa.services.image_api.DishApiImpl
import com.upjers.zoo2animalpa.ui.chat_bot.getChefGPTPrompt
import com.upjers.zoo2animalpa.ui.chat_bot.repository.ChatRepository
import com.upjers.zoo2animalpa.ui.chat_bot.systemRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DishArticleViewModel @Inject constructor(
    private val botRepository: ChatRepository,
    private val dishApiImpl: DishApiImpl
) : ViewModel() {
    private val _dishImagesFlow = MutableSharedFlow<List<SlideModel>>(1)
    val dishImagesFlow = _dishImagesFlow.asSharedFlow()

    private val _chatResponseFlow = MutableSharedFlow<String>(1)
    val chatResponseFlow = _chatResponseFlow.asSharedFlow()

    fun getImageList(articleName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var dishResponse: DishApiResponse?

            withContext(Dispatchers.Default) {
                dishResponse = dishApiImpl.requestDishPhoto(articleName, 5)
            }

            val convertedMap = mutableListOf<SlideModel>()

            dishResponse?.let {
                it.photos.forEach { photo ->
                    convertedMap.add(
                        SlideModel(
                            imageUrl = photo.src.landscape,
                            title = photo.photographer,
                            ScaleTypes.CENTER_CROP
                        )
                    )
                }
            }

            _dishImagesFlow.emit(convertedMap)
        }
    }

    fun getChatGeneratedArticle(articleName: String) {
        viewModelScope.launch {
            try {
                val mMessageList =
                    listOf(Message(role = systemRole, getChefGPTPrompt()), Message(content = articleName))

                val articleResponse = botRepository.getChatBotResponse(
                    userRequest = CompletionRequest(messages = mMessageList)
                )

                if (articleResponse != null) {
                    _chatResponseFlow.emit(articleResponse.choices[0].message.content)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}