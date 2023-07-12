package com.upjers.zoo2animalpa.services.image_service

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import java.io.File

interface WideWebImageRepo {
    fun getIntentForCamera(): Intent
    fun getImageBuilder(context: Context): File
    fun setImageIntentWithProvider(context: Context, intent: Intent, imageFile: File)
    fun createImageResult(result: ActivityResult?)
}