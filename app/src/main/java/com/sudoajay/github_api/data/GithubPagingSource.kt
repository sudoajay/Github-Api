package com.sudoajay.github_api.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sudoajay.github_api.api.GithubApiInterface
import com.sudoajay.github_api.api.GithubApiInterface.Companion.IN_QUALIFIER
import com.sudoajay.github_api.data.GithubRepository.Companion.NETWORK_PAGE_SIZE
import com.sudoajay.github_api.model.Github
import retrofit2.HttpException
import java.io.IOException

class GithubPagingSource(
    private val githubApiInterface: GithubApiInterface,
    private val query: String
) : PagingSource<Int, Github>() {

    var TAG = "GithubPagingSourceTAG"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Github> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = githubApiInterface.searchRepos(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) null
             else position + (params.loadSize / NETWORK_PAGE_SIZE)

            Log.e(TAG, "load: Item - Here I am  position" )
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            Log.e(TAG, "load: eroror - ${exception.toString()}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: eroror hu - ${exception.toString()}")

            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Github>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }
}
