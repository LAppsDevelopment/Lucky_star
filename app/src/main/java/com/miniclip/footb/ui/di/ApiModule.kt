package com.miniclip.footb.ui.di

import com.miniclip.footb.BuildConfig
import com.miniclip.footb.ui.intro_screen.repository.IntroRepository
import com.miniclip.footb.ui.services.network.RemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val serverHost = BuildConfig.SERVER_HOST
    private const val port = "8080"
    private const val API_BASE_URL = "http://$serverHost/$port"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RemoteApi = retrofit.create(RemoteApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiService: RemoteApi) = IntroRepository(apiService)
}