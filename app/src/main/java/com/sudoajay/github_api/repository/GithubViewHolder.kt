package com.sudoajay.github_api.repository


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sudoajay.github_api.R
import com.sudoajay.github_api.databinding.LayoutGithubItemBinding
import com.sudoajay.github_api.model.Github
import kotlin.math.ln
import kotlin.math.pow


class GithubViewHolder(    private val binding: LayoutGithubItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    var TAG = "GithubViewHolderTAG"
    fun bind(github: Github) {
        Log.e(TAG, "bind: value ghere  - ${github.url}")
        binding.repoName.text = github.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (github.description != null) {
            binding.repoDescription.text = github.description
            descriptionVisibility = View.VISIBLE
        }
        binding.repoDescription.visibility = descriptionVisibility

        binding.repoWatch.text = withSuffix(github.watcher)
        binding.repoStars.text = withSuffix(github.stars)
        binding.repoForks.text = withSuffix(github.forks)

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!github.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            binding.repoLanguage.text = resources.getString(R.string.language, github.language)
            languageVisibility = View.VISIBLE
        }
        binding.repoLanguage.visibility = languageVisibility

    }

    private fun withSuffix(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format(
            "%.1f %c",
            count / 1000.0.pow(exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }

    companion object {
        fun create(parent: ViewGroup): GithubViewHolder {
            val view = LayoutGithubItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return GithubViewHolder(view)
        }
    }
}