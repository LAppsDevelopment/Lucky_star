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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.miniclip.footb.databinding.FragmentDishRecipeBinding
import com.miniclip.footb.services.loadWithGlide
import com.miniclip.footb.viewmodels.DishRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishRecipeFragment : Fragment() {
    private val viewModel by viewModels<DishRecipeViewModel>()

    private var _binding: FragmentDishRecipeBinding? = null
    private val binding get() = _binding!!

    private val fabListener = View.OnClickListener {
        findNavController().popBackStack()
    }

    private val chatReceipt: DishRecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDishRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aiRecipe = chatReceipt.aiResponse
        val title = chatReceipt.userQuery

        setToolbarTitle(title)
        aiRecipe.setRecipeText()
        viewModel.setupAppBarImage(title)

        launchFlowCollector()

        binding.leaveFab.setOnClickListener(fabListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun launchFlowCollector() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dishImageFlow.collectLatest {
                        val dishImageUrl = it?.photos?.getOrNull(0)?.src?.landscape.toString()

                        with(requireActivity()) {
                            loadWithGlide(
                                url = dishImageUrl,
                                view = binding.dishImage
                            )
                        }
                    }
                }
            }
        }
    }

    private fun String.setRecipeText() {
        binding.recipeTextView.text = this
    }

    private fun setToolbarTitle(userQuery: String) {
        binding.collapsingToolbar.apply {
            title = userQuery.uppercase()
        }
    }
}
