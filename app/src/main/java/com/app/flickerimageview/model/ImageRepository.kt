package com.app.flickerimageview.model

import android.app.Application
import com.app.flickerimageview.network.BaseRepository
import com.app.flickerimageview.network.Output
import com.app.flickerimageview.network.Webservice
import com.app.flickerimageview.network.webservice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(application: Application) : BaseRepository(application) {

    private val client: Webservice = webservice

    suspend fun getResults(searchText: String, page: Int): Output<Any> {
        return enqueue(
            call = {
                 withContext(Dispatchers.IO) {
                     client.search(searchText, page)}
            }
        )
    }
}