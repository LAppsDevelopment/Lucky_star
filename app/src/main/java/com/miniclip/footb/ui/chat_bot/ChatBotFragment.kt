package com.miniclip.footb.ui.chat_bot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniclip.footb.databinding.FragmentChatBotBinding
import com.miniclip.footb.model.chat.ChatMessage
import com.miniclip.footb.model.open_ai_api.Message
import com.miniclip.footb.ui.chat_bot.adapter.ChatGPTAdapter
import com.miniclip.footb.ui.host.statusBarIconsColorChange
import com.miniclip.footb.viewmodels.ChatBotViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChatBotFragment : Fragment() {

    private var _binding: FragmentChatBotBinding? = null

    private val binding get() = _binding!!

    private val chatViewModel: ChatBotViewModel by viewModels()

    private lateinit var chatAdapter: ChatGPTAdapter
    private val chatMessagesList = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)

        statusBarIconsColorChange(requireActivity().window, lightIcons = false)

        chatAdapter = ChatGPTAdapter()
        chatAdapter.chatDataSet = chatMessagesList
        setupRecycleView()

        requestQuestion()

        return binding.root
    }

    // Refactor chat logic to handle notificationDataSetChanged in recycler
    private fun requestQuestion() {
        binding.btnAsk.setOnClickListener {
            val question = binding.askQuestionEditText.text.toString().trim()

            if (question.isNotBlank()) {
                insertUserMessageToChat(
                    message = question,
                    whoSentMessage = ChatMessage.SENT_BY_USER
                )
                binding.askQuestionEditText.setText("")
                askGPT(userQuestion = question)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Your question in blank, please write question",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun insertUserMessageToChat(
        message: String,
        whoSentMessage: String
    ) {
        chatMessagesList.add(ChatMessage(message, whoSentMessage))
        // message always inserted to the end of the list
        chatAdapter.notifyItemRangeChanged(0, chatAdapter.itemCount)
        binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.itemCount)
    }

    private fun askGPT(userQuestion: String) {
        chatMessagesList.add(ChatMessage("Typing...", ChatMessage.SENT_BY_CHAT))
        chatViewModel.askChatBot(listOf(Message(content = userQuestion)))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.chatState.collect { chatResponse ->
                    if (chatResponse != null) {
                        // Delete typing... message
                        chatMessagesList.remove(chatMessagesList.last())
                        Log.d("ASD", "chat response = $chatResponse")
                        insertUserMessageToChat(
                            message = chatResponse.choices[0].message.content,
                            whoSentMessage = ChatMessage.SENT_BY_CHAT
                        )
                    } else {
                        // Request failed show FAILED STATE
                    }
                }
            }
        }
    }

    private fun setupRecycleView() = binding.chatRecyclerView.apply {
        adapter = chatAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}