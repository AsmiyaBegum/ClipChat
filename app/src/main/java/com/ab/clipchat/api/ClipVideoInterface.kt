package com.ab.clipchat.api

import com.ab.clipchat.model.Category
import com.ab.clipchat.model.VideoStreamCategory
import retrofit2.Call
import retrofit2.http.GET

interface ClipVideoAPIInterface {

    @GET("v3/54ebbc48-6dd8-47a8-891c-59d2d1668987")
    fun getStreamCategoryList() : Call<List<Category>>

    @GET("v3/9aa08e52-d459-4a53-997c-b8ab36cbe527")
    fun getVideoStreamCategory() : Call<List<VideoStreamCategory>>

}