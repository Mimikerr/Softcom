package com.example.softcom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    val originalPrice: Double? = null,
    val category: String
)
