package com.miniclip.footb.services.open_ai

import com.miniclip.footb.model.open_ai_api.CompletionRequest
import com.miniclip.footb.model.open_ai_api.CompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGPTApi {
    @POST("/v1/chat/completions")
    suspend fun completion(@Body request: CompletionRequest): CompletionResponse
}