package com.example.ingsoftappmobiles.models

data class Album(
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val releaseYear:String,
    val excerpt:String
)