package com.sudoajay.github_api.repository


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.sudoajay.github_api.R
import com.sudoajay.github_api.databinding.LayoutGithubItemBinding
import com.sudoajay.github_api.model.Github


class GithubViewHolder(    private val binding: LayoutGithubItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    var TAG = "GithubViewHolderTAG"
    fun bind(github: Github) {
        Log.e(TAG, "bind: value ghere  - ${github.url}" )
        binding.repoName.text = github.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (github.description != null) {
            binding.repoDescription.text = github.description
            descriptionVisibility = View.VISIBLE
        }
        binding.repoDescription.visibility = descriptionVisibility

        binding.repoStars.text = github.stars.toString()
        binding.repoForks.text = github.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!github.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            binding.repoLanguage.text = resources.getString(R.string.language, github.language)
            languageVisibility = View.VISIBLE
        }
        binding.repoLanguage.visibility = languageVisibility

    }

    companion object {
        fun create(parent: ViewGroup): GithubViewHolder {
            val view =  LayoutGithubItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return GithubViewHolder(view)
        }
    }
}