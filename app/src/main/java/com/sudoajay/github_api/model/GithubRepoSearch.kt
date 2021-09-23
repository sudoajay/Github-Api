package com.sudoajay.github_api.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepoSearch(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Github> = emptyList(),
    val nextPage: Int? = null
): Parcelable