package com.miniclip.footb.services.open_ai

import com.miniclip.footb.model.CompletionRequest
import com.miniclip.footb.model.CompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGPTApi {
    @POST("/v1/chat/completions")
    suspend fun completion(@Body request: CompletionRequest): CompletionResponse
}