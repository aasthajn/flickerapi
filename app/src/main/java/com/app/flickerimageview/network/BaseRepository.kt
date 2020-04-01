package com.app.flickerimageview.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

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
                        "Could not fetch results.Please check internet connection or try again",
                        100,
                        "Fail"
                    )
                )
                else -> Output.Error(ErrorResponse("something went wrong", 100,"Fail"))
            }
        }
    }
}