package com.miniclip.footb.model.chat

data class ChatMessage(
    val message: String,
    val sentBy: String
) {
    companion object MessageType {
        const val SENT_BY_USER = "user_type"
        const val SENT_BY_CHAT = "chat_bot_type"
    }
}
