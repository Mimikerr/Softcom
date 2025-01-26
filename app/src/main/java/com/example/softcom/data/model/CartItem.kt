package com.example.softcom.data.model

data class CartItem(
    val product: Product,
    val quantity: Int,
    val observation: String? = null
)
