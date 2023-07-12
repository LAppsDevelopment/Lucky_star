package com.upjers.zoo2animalpa.services.image_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImageApiInstance {
    const val apiKey = "u45mzJHWuWxuh00lKP6WrNCWul0XiULepECeOpZSCvSmEwfo2gjuGfT3"
    private const val apiBaseUrl = "https://api.pexels.com/v1/"

    val imageApiInstance: MyImageApiRequests? by lazy {
        Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyImageApiRequests::class.java)
    }
}