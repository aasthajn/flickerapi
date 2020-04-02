package com.app.flickerimageview.utils

import android.content.Context
import android.net.*
import androidx.lifecycle.LiveData


class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    private var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

     fun isConnected():Boolean{
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork !=null && activeNetwork.isConnected
    }

    override fun onActive() {
        postValue(isConnected())
        registerBroadCastReceiver()
    }

    override fun onInactive() {
        unRegisterBroadCastReceiver()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            postValue(true)
        }

        override fun onLost(network: Network?) {
            postValue(false)
        }
    }
    private fun registerBroadCastReceiver() {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)

    }

    private fun unRegisterBroadCastReceiver() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}