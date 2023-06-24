package com.miniclip.footb.ui.chat_bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniclip.footb.R
import com.miniclip.footb.databinding.FragmentChatBotBinding
import com.miniclip.footb.model.chat.ChatMessage
import com.miniclip.footb.model.open_ai_api.Message
import com.miniclip.footb.ui.chat_bot.adapter.ChatGPTAdapter
import com.miniclip.footb.ui.host.statusBarIconsColorChange
import com.miniclip.footb.viewmodels.ChatBotViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChatBotFragment : Fragment(), ChatGPTAdapter.OnChatSuggestionItemClickListener {

    private var _binding: FragmentChatBotBinding? = null

    private val binding get() = _binding!!

    private val chatViewModel: ChatBotViewModel by viewModels()

    private lateinit var chatAdapter: ChatGPTAdapter
    private val chatMessagesList = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)

        requireActivity().window.clearFlags(FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = resources.getColor(R.color.color_chat_background)

        statusBarIconsColorChange(requireActivity().window, lightIcons = false)

        chatAdapter = ChatGPTAdapter(this@ChatBotFragment)
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
                    message = question, whoSentMessage = ChatMessage.SENT_BY_USER
                )
                binding.askQuestionEditText.setText("")
                askGPT(userQuestion = question)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Your question in blank, please write question",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun insertUserMessageToChat(
        message: String, whoSentMessage: String
    ) {
        chatMessagesList.add(ChatMessage(message = message, sentBy = whoSentMessage))
        // message always inserted to the end of the list
        chatAdapter.notifyItemRangeChanged(0, chatAdapter.itemCount)
        binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.itemCount)
    }

    private fun askGPT(userQuestion: String) {
        chatViewModel.savedUserQuery = userQuestion
        chatMessagesList.add(ChatMessage(message = "Typing...", sentBy = ChatMessage.SENT_BY_CHAT))

        chatViewModel.askChatBot(Message(content = userQuestion))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.chatState.collect { chatResponse ->
                    if (chatResponse != null) {
                        // Delete typing... message
                        chatMessagesList.remove(chatMessagesList.last())

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

    override fun onChatSuggestionClicked(cardView: View, refillId: String, message: String) {
        with(binding) {
            moreInfo.visibility = View.VISIBLE
            moreInfo.setOnClickListener {
                openSuggestionButton(message)
            }
            slideUpperBorderOfEditText()
        }
    }

    private fun FragmentChatBotBinding.slideUpperBorderOfEditText() {
        val bottomAnim =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_from_bottom_10)
        moreInfo.animation = bottomAnim
    }

    private fun openSuggestionButton(message: String) {
        val directions = ChatBotFragmentDirections.chatToReceipt(aiResponse = message, userQuery = chatViewModel.savedUserQuery)
        findNavController().navigate(directions)
        binding.moreInfo.visibility = View.GONE
    }
}