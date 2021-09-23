package com.sudoajay.github_api.api

import com.sudoajay.github_api.model.GithubRepoSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApiInterface {


    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubRepoSearch

    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val IN_QUALIFIER = "in:name,description"

    }



}