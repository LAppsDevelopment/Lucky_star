package com.upjers.zoo2animalpa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upjers.zoo2animalpa.model.ChatBotData
import com.upjers.zoo2animalpa.model.open_ai_api.CompletionRequest
import com.upjers.zoo2animalpa.model.open_ai_api.CompletionResponse
import com.upjers.zoo2animalpa.model.open_ai_api.Message
import com.upjers.zoo2animalpa.ui.chat_bot.getChefGPTPrompt
import com.upjers.zoo2animalpa.ui.chat_bot.repository.ChatRepository
import com.upjers.zoo2animalpa.ui.chat_bot.systemRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {
    private val chatBotData = ChatBotData()
    private val _chatState = MutableStateFlow<CompletionResponse?>(null)
    val chatState = _chatState.asStateFlow()

    var savedUserQuery: String
        get() = chatBotData.userQuery
        set(sd) {
            chatBotData.userQuery = sd
        }
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