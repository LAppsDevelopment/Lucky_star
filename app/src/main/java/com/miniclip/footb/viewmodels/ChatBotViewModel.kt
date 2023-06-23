package com.miniclip.footb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniclip.footb.model.open_ai_api.CompletionRequest
import com.miniclip.footb.model.open_ai_api.CompletionResponse
import com.miniclip.footb.model.open_ai_api.Message
import com.miniclip.footb.ui.chat_bot.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _chatState = MutableStateFlow<CompletionResponse?>(null)
    val chatState = _chatState.asStateFlow()

    fun askChatBot(userMessages: List<Message>) {
        viewModelScope.launch {
            try {
                val chatBotResponse = repository.getChatBotResponse(
                    userRequest = CompletionRequest(messages = userMessages)
                )

                if (chatBotResponse != null) {
                    _chatState.emit(chatBotResponse)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}