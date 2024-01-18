package com.example.ivcare.uzerdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Uzer::class],version = 2)
abstract class UzerDatabase : RoomDatabase() {

    abstract val uzerDAO : UzerDAO

    companion object{
        @Volatile
        private var INSTANCE : UzerDatabase? = null
        fun getInstance(context: Context):UzerDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UzerDatabase::class.java,
                        "uzer_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}