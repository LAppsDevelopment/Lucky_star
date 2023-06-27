package com.miniclip.footb.services

import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ComponentActivity.loadWithGlide(url: String?, view: ImageView) {
    lifecycleScope.launch(Dispatchers.Main.immediate) {
        Glide.with(this@loadWithGlide).load(url).centerCrop().into(view)
    }
}