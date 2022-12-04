package com.example.ingsoftappmobiles.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums_table")
data class Album(
    @PrimaryKey val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    var releaseYear:String,
    var excerpt:String
)