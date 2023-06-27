package com.miniclip.footb.di

import com.miniclip.footb.BuildConfig
import com.miniclip.footb.services.network.RemoteApi
import com.miniclip.footb.services.network.RetryRequestInterceptor
import com.miniclip.footb.ui.intro_screen.repository.IntroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val serverHost = BuildConfig.HOST
    private const val port = "8080"
    private const val API_BASE_URL = "http://$serverHost:$port/"
    private const val API_NAME = "API_NAME"
    private const val OK_HTTP_NAME = "API_NAME"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideRetryRequestInterceptor() = RetryRequestInterceptor()

    @Singleton
    @Provides
    @Named(OK_HTTP_NAME)
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        retryRequestInterceptor: RetryRequestInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(retryRequestInterceptor)
            .build()

    @Singleton
    @Provides
    @Named(API_NAME)
    fun provideRetrofit(@Named(OK_HTTP_NAME) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(@Named(API_NAME) retrofit: Retrofit): RemoteApi =
        retrofit.create(RemoteApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiService: RemoteApi) = IntroRepository(apiService)
}