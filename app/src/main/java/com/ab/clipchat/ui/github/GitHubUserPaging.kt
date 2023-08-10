package com.ab.clipchat.ui.github

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ab.clipchat.api.GitHubApiInterface
import com.ab.clipchat.model.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class GitHubUserPaging(private val apiService : GitHubApiInterface) : PagingSource<Int,GitHubUser>() {

    override fun getRefreshKey(state: PagingState<Int, GitHubUser>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
        return try {
            withContext(Dispatchers.IO){
                val currentPage = params.key?:1
                val response = apiService.getUsers(currentPage,30).execute()
                val data = response.body()?: emptyList()
                val responseData = mutableListOf<GitHubUser>()
                responseData.addAll(data)
                LoadResult.Page(
                    data = responseData,
                    prevKey = if(currentPage ==1)null else -1,
                    nextKey = currentPage.plus(1)
                )
            }
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}