package com.miniclip.footb.model

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.PROPERTY)
annotation class Redacted

/** A [Interceptor] ADD API TOKEN to requests. */
data class AuthInterceptor(private val method: String, @Redacted private val accessToken: String) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeader("Authorization", "$method $accessToken").build()
        return chain.proceed(request)
    }
}