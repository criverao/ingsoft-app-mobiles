package com.example.ingsoftappmobiles.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists_table")
data class Artist(
    @PrimaryKey val Id:Int,
    val name:String,
    var shortName:String,
    val image: String,
    val tipo:String
)
