package com.ab.clipchat.ui.profile

import androidx.lifecycle.ViewModel
import com.ab.clipchat.ClipChatApplication
import com.ab.clipchat.signIn.SignInService
import com.ab.clipchat.signIn.UserData
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject

class ProfileViewModel : ViewModel(),IProfileViewModel.Inputs,IProfileViewModel.Outputs {

    val inputs : IProfileViewModel.Inputs = this
    val outputs : IProfileViewModel.Outputs = this

    private val signInService = SignInService(ClipChatApplication.appContext()!!)


    private val userDataSubject : PublishSubject<UserData> = PublishSubject.create()


    override fun getUserData() {
        signInService.getUserData()
            .subscribe {
                userDataSubject.onNext(it)
            }
    }

    override fun signOut(logout: (Unit) -> Unit) {
        signInService.signOut()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                    logout(Unit)
            }
    }

    override fun userData(): Observable<UserData> {
       return userDataSubject
    }
}