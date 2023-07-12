package com.upjers.zoo2animalpa.services.network

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

@ObfustringThis
class RetryRequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null

        for (attempt in 1..5) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful) {
                    return response
                }
            } catch (e: IOException) {
                if (attempt == 5) {
                    throw e
                }
            }
            Thread.sleep(1000)
        }

        return response ?: throw IOException(ObfStr("comOupjersBzookanimalpa").v("Nsfzs ajxvj"))
    }
}