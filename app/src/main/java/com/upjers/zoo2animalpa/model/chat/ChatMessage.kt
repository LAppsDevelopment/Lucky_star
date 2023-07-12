package com.upjers.zoo2animalpa.model.chat

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val sentBy: String
) {
    companion object MessageType {
        const val SENT_BY_USER = "user_type"
        const val SENT_BY_CHAT = "chat_bot_type"
    }
}
