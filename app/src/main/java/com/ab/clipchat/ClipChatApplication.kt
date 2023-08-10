package com.ab.clipchat

import com.ab.clipchat.utils.Utils


import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.ab.clipchat.utils.NetworkChangeReceiver
import rx.subjects.PublishSubject
import kotlin.system.exitProcess

class ClipChatApplication : Application() {

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        if(!Utils.isBuildVariantDebug()){
            Thread.setDefaultUncaughtExceptionHandler { _, e ->
//                handleUncaughtException(e)
//                restartApp()
            }
        }

        networkChangeReceiver = NetworkChangeReceiver(networkSubject)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)

    }


    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkChangeReceiver)
    }


    private fun handleUncaughtException(e : Throwable){
        Log.e("app_crash",e.message.toString())
    }

    private fun restartApp() {
        val intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        exitProcess(1)
    }



    companion object {
        private var appContext: Context? = null
        private val networkSubject: PublishSubject<Boolean> = PublishSubject.create()

        fun appContext(): Context? {
            return appContext
        }

        fun getNetworkSubject(): PublishSubject<Boolean> {
            return networkSubject
        }
    }
}