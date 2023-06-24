package com.miniclip.footb.ui.menu

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.miniclip.footb.R
import com.miniclip.footb.databinding.FragmentMenuBinding
import com.miniclip.footb.ui.host.statusBarIconsColorChange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        statusBarIconsColorChange(requireActivity().window, lightIcons = true)
        requireActivity().window.statusBarColor = Color.BLACK

        binding.btnStartChat.setOnClickListener {
            findNavController().navigate(R.id.menu_to_chat)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}