package com.ab.clipchat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ab.clipchat.dao.UserDao
import com.ab.clipchat.signIn.UserData

@Database(entities = [UserData::class], version = 1)
abstract class ClipChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: ClipChatDatabase? = null

        fun getDatabase(context: Context): ClipChatDatabase {
            var tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                tempInstance = instance
                if (tempInstance == null) {
                    tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ClipChatDatabase::class.java,
                        "chipchat.db"
                    ).build()
                    instance = tempInstance
                }
            }

            return tempInstance!!
        }
    }
}



