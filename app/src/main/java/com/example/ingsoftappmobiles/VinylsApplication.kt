package com.example.ingsoftappmobiles

import android.app.Application
import com.example.ingsoftappmobiles.database.dao.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}