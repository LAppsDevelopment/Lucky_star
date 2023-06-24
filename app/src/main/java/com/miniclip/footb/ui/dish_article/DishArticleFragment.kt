package com.miniclip.footb.ui.dish_article

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.miniclip.footb.databinding.FragmentDishArticleBinding
import com.miniclip.footb.viewmodels.DishArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DishArticleFragment : Fragment() {
    private var _binding: FragmentDishArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DishArticleViewModel by viewModels()

    private val articleArguments: DishArticleFragmentArgs by navArgs()
    private var alphaObjectAnimator: ObjectAnimator? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleName = articleArguments.articleName


        alphaObjectAnimator = ObjectAnimator.ofFloat(binding.articleText, "alpha", 0.4f, 1.0f).apply {
            duration = 1000
            //set inifinite loop
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE

        }

        setArticleText(mText = getString(com.miniclip.footb.R.string.wait_a_second_i_m_thinking), animStart = true)
        viewModel.getChatGeneratedArticle(articleName)

        setArticleTitle(articleName)
        viewModel.getImageList(articleName)

        collectImagesFlow()
    }

    override fun onDestroy() {
        alphaObjectAnimator?.cancel()

        super.onDestroy()
        _binding = null
    }

    private fun collectImagesFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dishImagesFlow.collect {
                        binding.imageSlider.setImageList(it)
                    }
                }

                launch {
                    viewModel.chatResponseFlow.collect {
                        setArticleText(it, false)
                    }
                }
            }
        }
    }

    private fun setArticleTitle(mTitle: String) {
        binding.articleTitleText.text = mTitle
    }

    private fun setArticleText(mText: String, animStart: Boolean = true) {
        binding.articleText.text = mText

        if (animStart) {
            alphaObjectAnimator?.start()
        } else{
            alphaObjectAnimator?.cancel()
        }
    }
}