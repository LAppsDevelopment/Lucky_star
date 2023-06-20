package com.miniclip.footb.services.local_cache.repo

interface DataStoreRepository {
    suspend fun saveUrl(url: String?)
    suspend fun getUrl(): String?
}