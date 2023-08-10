package com.ab.clipchat.ui.videoclips

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ab.clipchat.model.Category
import com.ab.clipchat.model.VideoStreamCategory
import com.ab.clipchat.service.VideoClipService
import rx.Observable
import rx.subjects.PublishSubject

class VideoClipsViewModel ():ViewModel(),
    IVideoClipsViewModel.Inputs, IVideoClipsViewModel.Outputs {

    val videoclipservice : VideoClipService = VideoClipService()

    val input : IVideoClipsViewModel.Inputs = this
    val output : IVideoClipsViewModel.Outputs = this

    private val videoCategorySubject : PublishSubject<List<Category>> = PublishSubject.create()
    private val categorywiseClips : PublishSubject<List<VideoStreamCategory>> = PublishSubject.create()

    override fun getVideoCategoryList() {
        videoclipservice.getStreamCategoryList()
            .subscribe({
                videoCategorySubject.onNext(it)
            },{
                Log.d("Stream Category API", it.message.toString())
            })
    }

    override fun videoCategoryLst(): Observable<List<Category>> {
        return videoCategorySubject
    }

    override fun getCategorywiseVideoClips() {
        videoclipservice.getVideoStreamCategory()
            .subscribe({
                categorywiseClips.onNext(it)
            },{
                Log.d("Catgeorywise clip API", it.message.toString())
            })
    }

    override fun videoCategorywiseVideoClips(): Observable<List<VideoStreamCategory>> {
        return categorywiseClips
    }


}