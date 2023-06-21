package com.miniclip.footb.ui.dish_article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.miniclip.footb.R
import com.miniclip.footb.viewmodels.DishArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DishArticleFragment : Fragment() {
    private val viewModel: DishArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dish_article, container, false)
    }
}