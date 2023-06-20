package com.miniclip.footb.ui.services.local_cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.miniclip.footb.ui.services.local_cache.repo.DataStoreRepository
import kotlinx.coroutines.flow.first

class MyDataStoreImpl(val context: Context) : DataStoreRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

    override suspend fun saveUrl(url: String?) {
        val cachedUrl = getUrl()

        if (cachedUrl.isNullOrBlank() && !url.isNullOrEmpty()) {
            val cacheUrlKey = stringPreferencesKey(DATA_STORE_KEY)

            context.dataStore.edit { preferences ->
                preferences[cacheUrlKey] = url
            }
        }
    }

    override suspend fun getUrl(): String? {
        val savedUrl = stringPreferencesKey(DATA_STORE_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[savedUrl]
    }

    companion object {
        const val DATA_STORE_NAME = "data_store"
        const val DATA_STORE_KEY = "store_url"
    }
}
