package com.example.softcom.data.repository

import com.example.softcom.data.database.ProductDao
import com.example.softcom.data.model.Product

class ProductRepository(private val dao: ProductDao) {

    fun getProductsByCategory(category: String) = dao.getProductsByCategory(category)

    fun searchProducts(query: String) = dao.searchProducts(query)

    suspend fun insertProducts(products: List<Product>) {
        dao.insertAll(products)
    }
}
