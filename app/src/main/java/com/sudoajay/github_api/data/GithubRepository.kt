package com.sudoajay.github_api.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sudoajay.github_api.api.GithubApiInterface
import com.sudoajay.github_api.api.GithubInterfaceBuilder
import com.sudoajay.github_api.model.Github
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor() {

    fun getSearchResultStream(query: String): Flow<PagingData<Github>> {
        val apiInterface =
            GithubInterfaceBuilder.getApiInterface()
        Log.d("GithubRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(apiInterface!!, query) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}