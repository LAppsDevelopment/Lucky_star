package com.miniclip.footb.services.image_api

import android.util.Log
import com.miniclip.footb.model.api.DishApiResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DishApiImpl @Inject constructor() {
    suspend fun requestDishPhoto(
        userQuery: String,
        count: Int =1
    ): DishApiResponse? {
        val dishPhotoResponse = pushQuery(userQuery, count)

        return if (dishPhotoResponse?.body() != null && dishPhotoResponse.isSuccessful) {
            Log.e(TAG, "requestDishPhoto: ${dishPhotoResponse.body()}")
            dishPhotoResponse.body()
        } else {
            Log.e(TAG, "requestDishPhoto: m NULL")
            null
        }
    }

    private suspend fun pushQuery(userQuery: String, images: Int = 1) = try {
        ImageApiInstance.imageApiInstance?.getDishImage(
            userDish = userQuery,
            imagesCountPerPage = images
        )
    } catch (e: HttpException) {
        null
    } catch (e: IOException) {
        null
    }

    companion object {
        private const val TAG = "DishApiImpl"
    }
}