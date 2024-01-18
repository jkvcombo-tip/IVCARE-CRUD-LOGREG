package com.example.ivcare.userdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: UserEntity)

    @Query("SELECT * FROM user WHERE Username = :username AND Password = :password")
    fun getLoginDetails(username: String?, password: String?): LiveData<UserEntity>

    // New query to get a user by username
    @Query("SELECT * FROM user WHERE Username = :username")
    fun getUserByUsername(username: String): UserEntity?
}
