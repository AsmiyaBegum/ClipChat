package com.ab.clipchat.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ab.clipchat.signIn.UserData

@Dao
public interface UserDao {

    @Insert
    fun insertUser(user: UserData): Long

    @Query("SELECT * FROM UserData")
    fun getAllUsers(): List<UserData> // Change the return type here

    @Query("DELETE FROM UserData")
    fun deleteAllUsers()
}

