package com.miniclip.footb.di

import com.miniclip.footb.model.AuthInterceptor
import com.miniclip.footb.services.open_ai.ChatGPTApi
import com.miniclip.footb.ui.chat_bot.repository.ChatRepository
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
    // TODO ADD TO FIREBASE REMOTE CONFIG
    private const val OPEN_AI_KEY = "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"

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor("Bearer", OPEN_AI_KEY)

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