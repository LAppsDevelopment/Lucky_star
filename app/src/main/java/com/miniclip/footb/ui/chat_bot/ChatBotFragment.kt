package com.miniclip.footb.ui.chat_bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.miniclip.footb.databinding.FragmentChatBotBinding
import com.miniclip.footb.viewmodels.ChatBotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatBotFragment : Fragment() {

    private var _binding: FragmentChatBotBinding? = null

    private val binding get() = _binding!!

    private val chatViewModel: ChatBotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}