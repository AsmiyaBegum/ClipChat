package com.ab.clipchat.signIn

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.ab.clipchat.ClipChatApplication
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject

class DeviceRegisterationViewModel() : ViewModel(),IDeviceRegistrationViewModel.Inputs{

    val inputs : IDeviceRegistrationViewModel.Inputs = this


    private val signInService by lazy {
        SignInService(context = ClipChatApplication.appContext()!!)
    }

    override fun signIn(result: (Intent) -> Unit) {
        signInService.signIn()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {intent ->
                if (intent != null) {
                    result(intent)
                }
            }
    }

    override fun isUserLoggedIn() : Boolean {
        return signInService.isUserLoggedIn()
    }

    override fun getSignInResultFromIntent(intent: Intent, result: (SignInResult) -> Unit) {
        signInService.getSignInResultFromIntent(intent)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                result(it)
            }
    }



}