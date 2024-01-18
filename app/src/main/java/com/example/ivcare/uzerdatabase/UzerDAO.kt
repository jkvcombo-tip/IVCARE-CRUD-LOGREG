package com.example.ivcare.uzerdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UzerDAO {

    @Insert
    suspend fun insertUzer(uzer: Uzer) : Long

    @Update
    suspend fun updateUzer(uzer: Uzer) : Int

    @Delete
    suspend fun deleteUzer(uzer: Uzer) : Int

    @Query("DELETE FROM uzer_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM uzer_data_table")
    fun getAllUzers(): LiveData<List<Uzer>>
}