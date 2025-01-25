package com.example.softcom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.softcom.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
