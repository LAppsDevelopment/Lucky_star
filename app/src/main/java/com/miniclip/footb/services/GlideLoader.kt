package com.miniclip.footb.services

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun loadWithGlide(activity: Activity, url: String?, view: ImageView) {
    CoroutineScope(Dispatchers.Main.immediate).launch{
        Glide
            .with(activity)
            .load(url)
            .centerCrop()
            .into(view)
    }
}