package com.app.flickerimageview.network

sealed class Output<out T : Any>{
    data class Success<out T : Any>(val output : T) : Output<T>()
    data class Error(val errorMessage: ErrorResponse?)  : Output<Nothing>()
}