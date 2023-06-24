package com.miniclip.footb.model.open_ai_api

import com.google.gson.annotations.SerializedName

// Request to ChatGPT API with all user questions
data class CompletionRequest(val model: String = "gpt-3.5-turbo", val messages: List<Message>)

// User single question
data class Message(val role: String = "user", val content: String)

data class CompletionResponse(
    val id: String,
    @SerializedName("object")
    val objectString: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
) {

    data class Choice(
        val index: Int,
        val finish_reason: String,
        val message: Message,
    )

    data class Usage(
        val prompt_tokens: Int,
        val completion_tokens: Int,
        val total_tokens: Int,
    )
}