package com.example.ingsoftappmobiles.models

data class CollectorDetail(
    val collectorId: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val musicians: MutableList<Musician>
)
