package com.app.flickerimageview.utils

class Constants {

    companion object{
        const val TABLE_NAME = "flicker_app"
        const val DELETE_TABLE = "DELETE FROM "+ TABLE_NAME

        const val BASE_URL = "https://api.flickr.com/services/rest/"
        const val API_KEY = "062a6c0c49e4de1d78497d13a7dbb360"
        const val METHOD = "flickr.photos.search"
        const val FORMAT = "json"
        var PAGE_NO= 1
        var TOTAL_PAGES =1
        var PER_PAGE =5
    }
}