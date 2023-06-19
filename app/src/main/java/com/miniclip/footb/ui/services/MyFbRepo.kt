package com.miniclip.footb.ui.services

interface MyFbRepo {
    suspend fun getFetchedDeepLink(id: String?, token: String?): String?
}