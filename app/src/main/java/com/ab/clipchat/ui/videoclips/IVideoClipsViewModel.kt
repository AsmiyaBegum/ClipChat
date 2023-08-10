package com.ab.clipchat.ui.videoclips

import com.ab.clipchat.model.Category
import com.ab.clipchat.model.VideoStreamCategory
import rx.Observable

interface IVideoClipsViewModel {

    interface Inputs{
        fun getVideoCategoryList()
        fun getCategorywiseVideoClips()
    }

    interface Outputs{
        fun videoCategoryLst() : Observable<List<Category>>
        fun videoCategorywiseVideoClips() : Observable<List<VideoStreamCategory>>
    }
}