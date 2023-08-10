package com.ab.clipchat.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import rx.subjects.PublishSubject

class NetworkChangeReceiver(private val networkSubject: PublishSubject<Boolean>) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = isNetworkAvailable(context)
        networkSubject.onNext(isConnected)
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}