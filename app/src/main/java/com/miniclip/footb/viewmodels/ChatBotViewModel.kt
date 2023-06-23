package com.miniclip.footb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniclip.footb.model.open_ai_api.CompletionRequest
import com.miniclip.footb.model.open_ai_api.CompletionResponse
import com.miniclip.footb.model.open_ai_api.Message
import com.miniclip.footb.ui.chat_bot.getChefGPTPrompt
import com.miniclip.footb.ui.chat_bot.repository.ChatRepository
import com.miniclip.footb.ui.chat_bot.systemRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _chatState = MutableStateFlow<CompletionResponse?>(null)
    val chatState = _chatState.asStateFlow()

    fun askChatBot(userMessagePrompt: Message) {
        viewModelScope.launch {
            try {

                val chatGPTBehaviorPrompt = getChefGPTPrompt()

                val readyMessagesList =
                    listOf(Message(role = systemRole, chatGPTBehaviorPrompt), userMessagePrompt)

                val chatBotResponse = repository.getChatBotResponse(
                    userRequest = CompletionRequest(messages = readyMessagesList)
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