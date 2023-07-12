package com.upjers.zoo2animalpa.ui.menu.adapter.d_util

import androidx.recyclerview.widget.DiffUtil

class ArticlesDiffUtil: DiffUtil.ItemCallback<String>() {
    //implement with comparison logic
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.length == newItem.length
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
