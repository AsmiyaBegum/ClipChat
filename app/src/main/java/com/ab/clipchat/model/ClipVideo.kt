package com.ab.clipchat.model

data class Category(
    val categoryPictureUrl : String = "",
    val categoryName : String = ""
)

data class StreamDetails (
    var movieName : String = "",
    var thumbnail : String = ""
)

data class VideoStreamCategory (
    val streamName : String = "",
    val streamList : List<StreamDetails>  = listOf(),
)
