package com.ab.clipchat.signIn

import android.content.Intent

interface IDeviceRegistrationViewModel {
    interface Inputs{
        fun signIn(result : (Intent) -> (Unit))
        fun isUserLoggedIn() : Boolean
        fun getSignInResultFromIntent(intent: Intent, result : (SignInResult) ->(Unit))
    }
}