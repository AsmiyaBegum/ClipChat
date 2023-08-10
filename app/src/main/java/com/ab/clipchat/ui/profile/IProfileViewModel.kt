package com.ab.clipchat.ui.profile

import com.ab.clipchat.signIn.UserData
import rx.Observable

interface IProfileViewModel {
    interface Inputs{
        fun getUserData()
        fun signOut(logout : (Unit) -> (Unit))
    }

    interface Outputs{
        fun userData() : Observable<UserData>
    }
}