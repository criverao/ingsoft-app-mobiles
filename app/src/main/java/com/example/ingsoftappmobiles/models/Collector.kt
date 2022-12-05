package com.example.ingsoftappmobiles.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectors_table")
data class Collector(
    @PrimaryKey val collectorId: Int,
    val name:String,
    val telephone:String,
    val email:String
)
