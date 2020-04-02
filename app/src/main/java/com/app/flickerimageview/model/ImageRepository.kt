package com.app.flickerimageview.model

import android.app.Application
import com.app.flickerimageview.network.BaseRepository
import com.app.flickerimageview.network.Output
import com.app.flickerimageview.network.Webservice
import com.app.flickerimageview.network.webservice

class ImageRepository(application: Application) : BaseRepository() {

    private val client: Webservice = webservice

    suspend fun getResults(searchText: String, page: Int): Output<Any> {
        return enqueue(
            call = {
                client.search(searchText, page)
                //client.searchMock()
            }
        )
    }
}