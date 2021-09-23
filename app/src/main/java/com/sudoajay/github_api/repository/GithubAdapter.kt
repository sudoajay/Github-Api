package com.sudoajay.github_api.repository

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sudoajay.github_api.model.Github
import javax.inject.Inject

class GithubAdapter @Inject constructor() :
    PagingDataAdapter<Github, GithubViewHolder>(Person_COMPARATOR) {

    var TAG = "GithubAdapterTAG"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):GithubViewHolder {
       return  GithubViewHolder.create(parent)
        }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        Log.e(TAG, "onBindViewHolder: posit - $position")
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val Person_COMPARATOR = object : DiffUtil.ItemCallback<Github>() {
            override fun areItemsTheSame(oldItem: Github, newItem: Github): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Github, newItem: Github): Boolean =
                oldItem == newItem
        }
    }


}