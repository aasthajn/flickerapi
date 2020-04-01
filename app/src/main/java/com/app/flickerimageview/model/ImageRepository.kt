package com.app.flickerimageview.model

import android.app.Application
import com.app.flickerimageview.db.ImageDao
import com.app.flickerimageview.network.BaseRepository
import com.app.flickerimageview.network.Output
import com.app.flickerimageview.network.Webservice
import com.app.flickerimageview.network.webservice
import com.app.flickerimageview.db.ImageDatabase

class ImageRepository(application: Application) : BaseRepository() {

    private val client: Webservice = webservice
    private val database: ImageDatabase = ImageDatabase.getDatabase(application)!!
    private val imageDao: ImageDao

    init {
        imageDao = database.imageDao()
    }

    suspend fun getResults(searchText: String, page: Int): Output<Any> {
        return enqueue(
            call = {
                client.search(searchText, page)
            }
        )
    }
}