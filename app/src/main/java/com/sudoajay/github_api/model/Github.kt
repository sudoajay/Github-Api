package com.sudoajay.github_api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Github(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val url: String,
    @Json(name = "watchers_count") val watcher:Long,
    @Json(name = "stargazers_count") val stars: Long,
    @Json(name = "forks_count") val forks: Long,
    @Json(name = "language") val language: String?
) : Parcelable