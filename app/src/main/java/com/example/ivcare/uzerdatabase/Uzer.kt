package com.example.ivcare.uzerdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uzer_data_table")
data class Uzer (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uzer_name")
    val id : Int,

    @ColumnInfo(name = "uzer_id")
    var name : String,

    @ColumnInfo(name = "uzer_email")
    var email : String,

    @ColumnInfo(name = "uzer_status")
    var status : String,

    @ColumnInfo(name = "uzer_role")
    var role : String
)