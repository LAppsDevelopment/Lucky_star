package com.miniclip.footb.ui.dish_article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.miniclip.footb.databinding.FragmentDishArticleBinding
import com.miniclip.footb.viewmodels.DishArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishArticleFragment : Fragment() {
    private var _binding: FragmentDishArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DishArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo collect article name
        val articleName = "Spaghetti fenoment"

        setArticleTitle(articleName)
        viewModel.getImageList(articleName)

        collectImagesFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun collectImagesFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dishImagesFlow.collect {
                    binding.imageSlider.setImageList(it)
                }
            }
        }
    }

    //todo set text
    private fun setArticleTitle(mTitle: String) {
        binding.articleTitleText.text = mTitle
    }

    //todo set text
    private fun setArticleText(mText: String) {
        binding.articleText.text = mText
    }
}