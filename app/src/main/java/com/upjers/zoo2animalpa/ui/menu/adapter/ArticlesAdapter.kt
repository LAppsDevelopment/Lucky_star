package com.upjers.zoo2animalpa.ui.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.upjers.zoo2animalpa.databinding.LayoutMaterialCardBinding
import com.upjers.zoo2animalpa.services.image_api.DishApiImpl
import com.upjers.zoo2animalpa.services.loadWithGlide
import com.upjers.zoo2animalpa.ui.menu.MenuFragment
import com.upjers.zoo2animalpa.ui.menu.MenuFragmentDirections
import com.upjers.zoo2animalpa.ui.menu.adapter.d_util.ArticlesDiffUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/* Adapter for Menu RV */
class ArticlesAdapter(
    private val menuFragment: MenuFragment,
    private val dishApiImpl: DishApiImpl
) :
    ListAdapter<String, ArticlesViewHolder>(ArticlesDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            LayoutMaterialCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        with(holder) {
            val string: String? = getItem(position)

            if (string != null) {
                articleTitleInstance.text = string

                articleImage.setApiImage(string)

                buttonInstance.setButtonListener(string)
            } else {
                mRoot.visibility = ViewGroup.GONE
            }
        }
    }

    private fun ImageView.setApiImage(string: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var imageUrl: String? = null
            async {
                imageUrl =
                    dishApiImpl.requestDishPhoto(string)?.photos?.getOrNull(0)?.src?.landscape
            }.await()

            with(menuFragment.requireActivity()) {
                loadWithGlide(
                    url = imageUrl,
                    view = this@setApiImage
                )
            }
        }
    }

    private fun TextView.setButtonListener(string: String) {
        setOnClickListener {
           val direction = MenuFragmentDirections.menuToArticle(string)
            menuFragment.findNavController().navigate(direction)
        }
    }
}

class ArticlesViewHolder(mView: LayoutMaterialCardBinding) : RecyclerView.ViewHolder(mView.root) {
    val mRoot = mView.root
    val buttonInstance = mView.openButton
    val articleTitleInstance = mView.articleNameText
    val articleImage = mView.articleImage
}
