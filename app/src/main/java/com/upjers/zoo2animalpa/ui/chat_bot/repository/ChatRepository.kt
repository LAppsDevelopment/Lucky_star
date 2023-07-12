package com.upjers.zoo2animalpa.ui.chat_bot.repository

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.upjers.zoo2animalpa.model.open_ai_api.CompletionRequest
import com.upjers.zoo2animalpa.model.open_ai_api.CompletionResponse
import com.upjers.zoo2animalpa.services.open_ai.ChatGPTApi
import javax.inject.Inject

class ChatRepository @Inject constructor(private val chatAPI: ChatGPTApi) {

    suspend fun getChatBotResponse(userRequest: CompletionRequest): CompletionResponse? = try {
        chatAPI.simpleChatGPTQuestion(userRequest)
    } catch (e: Exception) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        null
    }
}