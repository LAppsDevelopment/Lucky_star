package com.miniclip.footb.ui.dish_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miniclip.footb.databinding.FragmentDishRecipeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DishRecipeFragment : Fragment() {
    private var _binding: FragmentDishRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDishRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}