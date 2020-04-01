package com.app.flickerimageview.model

data class PhotosResponse(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>?,
    val total: String
)