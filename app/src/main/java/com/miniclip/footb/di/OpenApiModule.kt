package com.miniclip.footb.di

import com.miniclip.footb.model.AuthInterceptor
import com.miniclip.footb.services.open_ai.ChatGPTApi
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
    private const val OPEN_AI_KEY = "yV8BE0TrLd4UwlSoeufMT3BlbkFJ5fSXtflvcSjlQ5RYzfut"

    /* TODO probably check " "*/
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
    fun provideOpenAiApiService(retrofit: Retrofit): ChatGPTApi = retrofit.create(ChatGPTApi::class.java)

    @Singleton
    @Provides
    fun provideOpenAiRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API_OPEN_AI_URL)
        .client(okHttpClient)
        .build()
}