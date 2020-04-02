package com.app.flickerimageview.network

import android.app.Application
import com.app.flickerimageview.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException

open class BaseRepository(val application: Application) {

    suspend fun <T : Any> enqueue(call: suspend () -> Response<T>): Output<T> {
        return apiOutput(call)
    }

    private suspend fun <T : Any> apiOutput(call: suspend () -> Response<T>): Output<T> {
        try {
            val response = call.invoke()
            return if (response.isSuccessful)
                Output.Success(response.body()!!)
            else {
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                Output.Error(errorResponse)
            }
        } catch (t: Throwable) {
            return when (t) {
                is IOException -> Output.Error(
                    ErrorResponse(
                        application.resources.getString(R.string.error_internet),
                        100,
                        application.resources.getString(R.string.fail)
                    )
                )
                else -> Output.Error(ErrorResponse(application.resources.getString(R.string.something_wrong), 100, application.resources.getString(R.string.fail)))
            }
        }
    }
}