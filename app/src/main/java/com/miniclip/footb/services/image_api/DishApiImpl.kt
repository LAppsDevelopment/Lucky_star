package com.miniclip.footb.services.image_api

import android.util.Log
import com.miniclip.footb.model.api.DishApiResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DishApiImpl @Inject constructor() {
    suspend fun requestDishPhoto(
        userQuery: String
    ): DishApiResponse? {
        val dishPhotoResponse = pushQuery(userQuery)

        return if (dishPhotoResponse?.body() != null && dishPhotoResponse.isSuccessful) {
            Log.e(TAG, "requestDishPhoto: ${dishPhotoResponse.body()}")
            dishPhotoResponse.body()
        } else {
            Log.e(TAG, "requestDishPhoto: m NULL")
            null
        }
    }

    private suspend fun pushQuery(userQuery: String) = try {
        ImageApiInstance.imageApiInstance?.getDishImage(
            userDish = userQuery
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