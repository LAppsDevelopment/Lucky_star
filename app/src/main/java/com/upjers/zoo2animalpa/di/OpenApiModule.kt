package com.upjers.zoo2animalpa.di

import com.upjers.zoo2animalpa.model.AuthInterceptor
import com.upjers.zoo2animalpa.model.open_ai_api.HolderManager
import com.upjers.zoo2animalpa.services.open_ai.ChatGPTApi
import com.upjers.zoo2animalpa.ui.chat_bot.repository.ChatRepository
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
object OpenApiModule {
    private const val API_OPEN_AI_URL = "https://api.openai.com/"

    @Provides
    @Singleton
    fun provideRemoteHolder(): HolderManager = HolderManager()

    @Singleton
    @Provides
    fun provideAuthInterceptor(holderManager: HolderManager): AuthInterceptor =
        AuthInterceptor("Bearer", holderManager.remoteKeyHolder.openAPIKey)

    @Singleton
    @Provides
    fun providesOpenAiOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideOpenAiApiService(retrofit: Retrofit): ChatGPTApi =
        retrofit.create(ChatGPTApi::class.java)

    @Singleton
    @Provides
    fun provideOpenAiRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API_OPEN_AI_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideChatRepository(chatGPTService: ChatGPTApi) = ChatRepository(chatGPTService)
}