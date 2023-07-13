package com.upjers.zoo2animalpa.model.api

import androidx.annotation.Keep

@Keep
data class DishApiResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)