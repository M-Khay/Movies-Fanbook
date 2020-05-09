package com.yourself.searchyourcityweather.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi


object NetworkUtils {
    // Network Check
    fun registerNetworkCallback(context: Context) {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(),
                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        NetworkConnectivity.isNetworkConnected = true // Global Static Variable
                    }
                    override fun onLost(network: Network) {
                        NetworkConnectivity.isNetworkConnected = false // Global Static Variable
                    }
                })
            NetworkConnectivity.isNetworkConnected = false
        } catch (e: Exception) {
            NetworkConnectivity.isNetworkConnected = false
        }
    }


}

object NetworkConnectivity {
    // Global variable used to store network state
    var isNetworkConnected = false
}

