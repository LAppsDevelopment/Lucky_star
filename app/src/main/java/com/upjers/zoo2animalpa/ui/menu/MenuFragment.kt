package com.upjers.zoo2animalpa.ui.menu

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.upjers.zoo2animalpa.R
import com.upjers.zoo2animalpa.databinding.FragmentMenuBinding
import com.upjers.zoo2animalpa.model.ArticlesNamesData
import com.upjers.zoo2animalpa.services.image_api.DishApiImpl
import com.upjers.zoo2animalpa.ui.host.AppContainerActivity
import com.upjers.zoo2animalpa.ui.host.statusBarIconsColorChange
import com.upjers.zoo2animalpa.ui.menu.adapter.ArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var dishApiImpl: DishApiImpl
    private lateinit var articlesAdapter: ArticlesAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesAdapter = ArticlesAdapter(menuFragment = this, dishApiImpl = dishApiImpl)
        setupArticlesRv()
        observeBackPressInMenu()
    }

    private fun setupArticlesRv() {
        binding.articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articlesAdapter
        }

        articlesAdapter.submitList(ArticlesNamesData().articlesList)
    }

    private fun observeBackPressInMenu() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as AppContainerActivity).killApp()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}