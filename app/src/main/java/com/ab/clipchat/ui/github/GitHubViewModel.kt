package com.ab.clipchat.ui.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ab.clipchat.api.RetrofitWrapper

class GitHubViewModel() : ViewModel(){

    private val gitHubApi = RetrofitWrapper.gitHubApiInterface

    val listData = Pager(PagingConfig(pageSize = 1)){
        GitHubUserPaging(gitHubApi)
    }.flow.cachedIn(viewModelScope)


}