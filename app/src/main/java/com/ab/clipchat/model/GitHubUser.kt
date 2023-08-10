package com.ab.clipchat.model

import com.google.gson.annotations.Expose

data class GitHubUser(
    @Expose val login : String,
    @Expose val id : Long,
    @Expose val avatarUrl : String,
    @Expose val htmlUrl : String
)
