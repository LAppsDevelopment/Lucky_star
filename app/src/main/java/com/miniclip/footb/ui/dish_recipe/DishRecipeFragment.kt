package com.miniclip.footb.ui.dish_recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.miniclip.footb.databinding.FragmentDishRecipeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DishRecipeFragment : Fragment() {
    private var _binding: FragmentDishRecipeBinding? = null
    private val binding get() = _binding!!

    private val chatReceipt: DishRecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // TODO Chat response with full receipt string
        val getReceipt = chatReceipt.aiResponse
        Log.d("ASD", "MESSAGE = $getReceipt")

        _binding = FragmentDishRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}