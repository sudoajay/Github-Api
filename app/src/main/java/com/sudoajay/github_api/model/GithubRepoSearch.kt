package com.sudoajay.github_api.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GithubRepoSearch(
    @Json(name = "total_count") val total: Int = 0,
    @Json(name = "items") val items: List<Github> = emptyList(),
    val nextPage: Int? = null
): Parcelable