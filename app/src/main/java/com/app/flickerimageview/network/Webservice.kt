package com.app.flickerimageview.network

import com.app.flickerimageview.model.SearchItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    @GET(".")
    suspend fun search(@Query("text") searchText: String,
                       @Query("api_key") apikey: String,
                       @Query("page") pageNumber: Int,
                       @Query("per_page") pageCapacity :Int,
                       @Query("method") method:String,
                       @Query("format") format:String,
                       @Query("nojsoncallback") nojsoncallback:Int): Response<SearchItem>


}