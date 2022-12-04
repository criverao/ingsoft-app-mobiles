package com.example.ingsoftappmobiles.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists_table")
data class ArtistDetail(
    @PrimaryKey val Id:Int,
    val name:String,
    var shortName:String,
    val image: String,
    val description:String,
    val creationBrithDate:String,
    val tipo:String,
    val albums: MutableList<Album>,
    val prizes: MutableList<Prize>
)
