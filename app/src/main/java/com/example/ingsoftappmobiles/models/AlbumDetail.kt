package com.example.ingsoftappmobiles.models

data class AlbumDetail(
    val albumId:Int,
    val name:String,
    val cover:String,
    var releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
)