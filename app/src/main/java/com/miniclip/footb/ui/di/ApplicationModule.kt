package com.miniclip.footb.ui.di

import android.content.Context
import com.miniclip.footb.ui.services.local_cache.MyDataStoreImpl
import com.miniclip.footb.ui.services.params.config.MyConfigImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = MyDataStoreImpl(context)

    @Singleton
    @Provides
    fun provideMyConfigImpl() = MyConfigImpl()
}