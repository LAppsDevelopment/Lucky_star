package com.miniclip.footb.services.image_api

import com.miniclip.footb.model.api.DishApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyImageApiRequests {

    @GET("search")
    suspend fun getDishImage(
        @Header("Authorization") apiKey: String = ImageApiInstance.apiKey,
        @Query("query") userDish: String,
        @Query("page") pageWithImagesCount: Int = 1,
        @Query("per_page") imagesCountPerPage: Int = 1,
    ): Response<DishApiResponse>
}