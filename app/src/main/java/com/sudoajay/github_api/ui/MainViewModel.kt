package com.sudoajay.github_api.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sudoajay.github_api.data.GithubRepository
import com.sudoajay.github_api.model.Github
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var TAG = "MainViewModelTAG"
    @Inject
    lateinit var repository: GithubRepository

    var hideProgress: MutableLiveData<Boolean> = MutableLiveData()
    var listItem :MutableLiveData<PagingData<Github>> = MutableLiveData()

    init {
        loadHideProgress()

    }

    private fun loadHideProgress() {
        hideProgress.value = false
    }

    fun searchValue(value: String) {
        viewModelScope.launch {
            repository.getSearchResultStream(value).collectLatest {
                Log.e(TAG, "searchValue: value here " )
                listItem.postValue(it)
            }
        }
    }

}