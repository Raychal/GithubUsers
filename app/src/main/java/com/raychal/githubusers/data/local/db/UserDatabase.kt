package com.raychal.githubusers.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raychal.githubusers.model.GithubUser

@Database(entities = [GithubUser::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val mInstance = INSTANCE
            if (mInstance != null)
                return mInstance

            synchronized(UserDatabase::class.java){
                val mbuilder = Room.databaseBuilder(
                    context.applicationContext, UserDatabase::class.java, "database_github"
                ).build()
                INSTANCE = mbuilder
                return mbuilder
            }
        }
    }
    abstract fun userDao(): UserDao
}