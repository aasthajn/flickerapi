package com.app.flickerimageview.network

import com.app.flickerimageview.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


val webservice: Webservice by lazy {

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = (HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(Webservice::class.java)
}
