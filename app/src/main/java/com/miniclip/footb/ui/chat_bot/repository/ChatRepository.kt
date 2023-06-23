package com.miniclip.footb.ui.chat_bot.repository

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.miniclip.footb.model.open_ai_api.CompletionRequest
import com.miniclip.footb.model.open_ai_api.CompletionResponse
import com.miniclip.footb.services.open_ai.ChatGPTApi
import javax.inject.Inject

class ChatRepository @Inject constructor(private val chatAPI: ChatGPTApi) {

    suspend fun getChatBotResponse(userRequest: CompletionRequest): CompletionResponse? = try {
        chatAPI.completion(userRequest)
    } catch (e: Exception) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        null
    }
}