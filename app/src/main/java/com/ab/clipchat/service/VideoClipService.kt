package com.ab.clipchat.service

import com.ab.clipchat.api.RetrofitWrapper
import com.ab.clipchat.model.Category
import com.ab.clipchat.model.VideoStreamCategory
import rx.Observable
import rx.schedulers.Schedulers


class VideoClipService  {

    private val streamVerseService = RetrofitWrapper.clipVideoApiInterface

     fun getStreamCategoryList(): Observable<List<Category>> {
         return Observable.just(Unit)
             .observeOn(Schedulers.io())
             .map {
                 val response = streamVerseService.getStreamCategoryList().execute()
                 response.body()
             }
    }


     fun getVideoStreamCategory(): Observable<List<VideoStreamCategory>> {
        return  Observable.just(Unit)
            .observeOn(Schedulers.io())
            .map {
                val response = streamVerseService.getVideoStreamCategory().execute()
                response.body()
            }
    }


}