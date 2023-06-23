package com.miniclip.footb.ui.dish_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.miniclip.footb.R
import com.miniclip.footb.databinding.FragmentDishRecipeBinding
import com.miniclip.footb.viewmodels.DishRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishRecipeFragment : Fragment() {
    private val viewModel by viewModels<DishRecipeViewModel>()

    private var _binding: FragmentDishRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDishRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pushImageSearch("spagetti")

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dishImageFlow.collectLatest {
                        //todo set image

                        val url = it?.photos?.getOrNull(0)?.src?.landscape

                        setupToolbarImage(url)
                    }
                }
            }
        }
    }

    private fun setupToolbarImage(url: String?) {
        Glide
            .with(requireActivity())
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.dishImage);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}