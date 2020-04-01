package com.app.flickerimageview.network

import com.app.flickerimageview.model.SearchItem
import com.app.flickerimageview.utils.Constants.API_KEY
import com.app.flickerimageview.utils.Constants.FORMAT
import com.app.flickerimageview.utils.Constants.METHOD
import com.app.flickerimageview.utils.Constants.PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    @GET(".")
    suspend fun search(
        @Query("text") searchText: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageCapacity: Int = PER_PAGE,
        @Query("api_key") apikey: String = API_KEY,
        @Query("method") method: String = METHOD,
        @Query("format") format: String = FORMAT,
        @Query("nojsoncallback") nojsoncallback: Int = 1
    ): Response<SearchItem>


}