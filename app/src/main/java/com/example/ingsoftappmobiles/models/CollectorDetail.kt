package com.example.ingsoftappmobiles.models

data class CollectorDetail(
    val collectorId: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val albums: MutableList<CollectorAlbum>,
    val musicians: MutableList<Musician>
)
