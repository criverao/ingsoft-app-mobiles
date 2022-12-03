package com.example.ingsoftappmobiles.models

data class Album(
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    var releaseYear:String,
    var excerpt:String
)