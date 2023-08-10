package com.ab.clipchat.service

import com.ab.clipchat.api.GitHubApiInterface
import com.ab.clipchat.api.RetrofitWrapper
import com.ab.clipchat.model.GitHubUser
import rx.Observable
import rx.schedulers.Schedulers

class GitHubService() {

    private val gitHubApi = RetrofitWrapper.gitHubApiInterface

    fun githubUserList() : Observable<List<GitHubUser>> {
        return Observable.just(Unit)
            .observeOn(Schedulers.io())
            .map {
                val response = gitHubApi.getUsers(0, 30).execute()
                response.body()
            }
    }
}