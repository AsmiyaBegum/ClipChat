package com.ab.clipchat.signIn

import android.content.Context
import android.content.Intent
import android.util.Log
import com.ab.clipchat.R
import com.ab.clipchat.database.ClipChatDatabase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import rx.Observable
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SignInService(private val context : Context) {

    private val auth = Firebase.auth
    private var googleSignInOptions : GoogleSignInOptions = getGoogleSignInOption()
    private val fireStore = FirebaseFirestore.getInstance()

    private fun getGoogleSignInOption() : GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_web_client_id))
            .requestEmail()
            .build()
    }

     fun signIn() : Observable<Intent?> {
         return Observable.just(Unit)
             .observeOn(Schedulers.io())
             .map {
                 val result = try {
                     GoogleSignIn.getClient(context,googleSignInOptions).signInIntent
                 } catch (e : Exception){
                     e.printStackTrace()
                     if(e is CancellationException) throw e
                     null
                 }
                  result
             }
    }

     fun signOut() : Observable<Unit> {
         return Observable.just(Unit)
             .observeOn(Schedulers.io())
             .map {
                 try {
                     auth.signOut()
                     GoogleSignIn.getClient(context,googleSignInOptions).signOut()
                 }catch (e: Exception){
                     e.printStackTrace()
                     if(e is CancellationException) throw e
                 }
                 val db = ClipChatDatabase.getDatabase(context)
                 db.userDao().deleteAllUsers()
             }
    }

    fun isUserLoggedIn() : Boolean {
        return auth.currentUser != null
    }

    fun getCurrentSignedInUser() : UserData? = auth.currentUser?.run {
        UserData(
            userId =  uid,
            userName = displayName?:"",
            profilePicture = photoUrl.toString(),
            email = email?:"",
            phoneNumber = phoneNumber?:""
        )
    }

     fun getSignInResultFromIntent(intent: Intent) : Observable<SignInResult> {
         return Observable.just(Unit)
             .observeOn(Schedulers.io())
             .map {
                 val loggedInUserCredential = GoogleSignIn.getSignedInAccountFromIntent(intent)
                 val account: GoogleSignInAccount = loggedInUserCredential.getResult(ApiException::class.java)
                 val googleIdToken = GoogleAuthProvider.getCredential(account.idToken,null)
                 try {
                     runBlocking {
                         val user = auth.signInWithCredential(googleIdToken).await().user
                         val signInResult = SignInResult(
                             data = user?.run {
                                 UserData(
                                     userId =   uid,
                                     userName = displayName?:"",
                                     profilePicture = photoUrl?.toString()?:"",
                                     email = email?:"",
                                     phoneNumber = phoneNumber?:"",
                                     loggedInTime = getTime()
                                 )
                             },
                             errorMessage = null
                         )
                         postLoginDetailToFireStore(account.idToken?:"",signInResult)
                         updateUserDataInRoom(signInResult.data!!)
                         signInResult
                     }

                 }catch (e : Exception) {
                     e.printStackTrace()
                     if(e is CancellationException) throw e
                     SignInResult(data = null, errorMessage = e.message)
                 }
             }
    }
    private fun getTime() : String {
        val storedDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        val parsedDate: Date = storedDateFormat.parse(Date().toString())

        val displayFormat = SimpleDateFormat("MMM d (hh:mma)", Locale.getDefault())
        return displayFormat.format(parsedDate)
    }

    private fun updateUserDataInRoom(userData: UserData){
        val db = ClipChatDatabase.getDatabase(context)
        db.userDao().insertUser(userData)
    }

    private fun postLoginDetailToFireStore(userToken : String,signInResult : SignInResult) {
        val documentRef = fireStore.collection("user").document(userToken)
        val userDetail: Map<String, String> = mapOf(
            "name" to signInResult.data!!.userName,
            "email" to signInResult.data.email,
            "phoneNumber" to signInResult.data.phoneNumber,
            "profilePicture" to signInResult.data.profilePicture
        )
        documentRef.set(userDetail).addOnSuccessListener {
            Log.d("FireStore","Details Successfully Posted in FireStore")
        }
            .addOnFailureListener {
                Log.d("FireStore","Details Posting failed in FireStore with Exception ${it.printStackTrace()}")
            }
    }

    fun getUserData() : Observable<UserData>{
        return Observable.just(Unit)
            .observeOn(Schedulers.io())
            .map {
                val db = ClipChatDatabase.getDatabase(context)
                db.userDao().getAllUsers()[0]
            }
    }


}