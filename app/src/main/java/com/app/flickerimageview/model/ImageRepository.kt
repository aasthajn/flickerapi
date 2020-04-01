package com.app.flickerimageview.model

import android.app.Application
import com.app.flickerimageview.db.ImageDao
import com.app.movieapp.db.ImageDatabase
import com.app.flickerimageview.network.BaseRepository
import com.app.flickerimageview.network.Output
import com.app.flickerimageview.network.Webservice
import com.app.flickerimageview.network.webservice
import com.app.flickerimageview.utils.Constants
import com.app.flickerimageview.utils.Constants.Companion.API_KEY
import com.app.flickerimageview.utils.Constants.Companion.FORMAT
import com.app.flickerimageview.utils.Constants.Companion.METHOD
import com.app.flickerimageview.utils.Constants.Companion.PER_PAGE

class ImageRepository(application: Application) : BaseRepository(){

    private val client: Webservice = webservice
    private val database: ImageDatabase = ImageDatabase.getDatabase(application)!!
    private val imageDao: ImageDao

    init {
        imageDao = database.imageDao()
    }

    suspend fun getResults(searchText: String) : Output<Any> {
        return enqueue(
            call = {client.search(searchText, API_KEY, Constants.PAGE_NO, PER_PAGE, METHOD, FORMAT,1)}
        )
    }
}