package com.raychal.githubusers.data.local.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.raychal.githubusers.model.GithubUser

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY login ASC")
    fun getUserList(): LiveData<List<GithubUser>>

    @Query("SELECT * FROM user_table WHERE login = :username")
    fun getUserDetail(username : String): GithubUser?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: GithubUser)

    @Delete
    suspend fun deleteUser(model: GithubUser): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table ORDER BY login ASC")
    fun getUserListProvider(): Cursor
}