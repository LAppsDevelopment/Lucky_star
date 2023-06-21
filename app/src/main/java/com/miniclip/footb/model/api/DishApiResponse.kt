package com.miniclip.footb.model.api

data class DishApiResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)