package com.ab.clipchat.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import com.ab.clipchat.BuildConfig
import com.ab.clipchat.ClipChatApplication
import com.ab.clipchat.R
import java.util.Locale

//import com.ab.clipchat.BuildConfig


object Utils {

    fun View.showVisibility(condition: Boolean) {
        this.visibility = if (condition) View.VISIBLE else View.GONE
    }

    fun isBuildVariantDebug(): Boolean = BuildConfig.BUILD_TYPE == Constants.BUILD_TYPE_DEBUG

    fun String.capitalizeFirstChar() : String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }

    fun checkDeviceOnline(context: Context) : Boolean {
        if(!checkInternetConnection()){
            Toast.makeText(context,context.getString(R.string.device_offline),Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager = ClipChatApplication.appContext()
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = connectivityManager.activeNetworkInfo?.isConnected
        return isConnected ?: false
    }

}