package com.ab.clipchat.signIn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

@Entity
data class UserData (
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo
    val userId: String,
    @ColumnInfo
    val userName: String,
    @ColumnInfo
    val profilePicture: String,
    @ColumnInfo
    val email : String,
    @ColumnInfo
    val phoneNumber : String,
    @ColumnInfo
    val loggedInTime : String = ""
    )