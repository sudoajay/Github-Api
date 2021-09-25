package com.sudoajay.github_api.api

import com.sudoajay.github_api.api.GithubApiInterface.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class GithubInterfaceBuilder {
    companion object {
        var githubApiInterface: GithubApiInterface? = null
        private var okHttpClient: OkHttpClient? = null


        fun getApiInterface(): GithubApiInterface? {
            if (githubApiInterface == null) {
                //For printing API url and body in logcat
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .callTimeout(50, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(okHttpClient!!)
                    .build()
                githubApiInterface = retrofit.create(GithubApiInterface::class.java)
            }

            return githubApiInterface
        }

    }

}