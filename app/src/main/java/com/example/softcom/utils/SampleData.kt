package com.softcom.utils

import com.example.softcom.data.model.Product

object SampleData {
    val products = listOf(
        Product(1, "Notebook", "Notebook Dell XPS", 4500.0, "https://via.placeholder.com/150", "Eletrônicos"),
        Product(2, "Smartphone", "iPhone 13", 6500.0, "https://via.placeholder.com/150", "Eletrônicos"),
        Product(3, "Cadeira Gamer", "Cadeira Ergonômica", 800.0, "https://via.placeholder.com/150", "Móveis")
    )
}
