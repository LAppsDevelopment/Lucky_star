package com.upjers.zoo2animalpa.services.open_ai

import com.upjers.zoo2animalpa.model.open_ai_api.CompletionRequest
import com.upjers.zoo2animalpa.model.open_ai_api.CompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGPTApi {
    @POST("/v1/chat/completions")
    suspend fun simpleChatGPTQuestion(@Body request: CompletionRequest): CompletionResponse
}