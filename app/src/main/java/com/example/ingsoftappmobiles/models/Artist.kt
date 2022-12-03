package com.example.ingsoftappmobiles.models

data class Artist(
    val Id:Int,
    val name:String,
    var shortName:String,
    val image: String,
    val description:String,
    val creationBrithDate:String,
    val tipo:String,
    val albums: MutableList<Album>,
    val prizes: MutableList<Prize>
)
