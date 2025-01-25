package com.example.softcom.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.softcom.data.model.Product

@Dao
interface ProductDao {

    // Insere uma lista de produtos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    // Consulta todos os produtos de uma categoria específica
    @Query("SELECT * FROM product WHERE category = :category")
    fun getProductsByCategory(category: String): LiveData<List<Product>>

    // Pesquisa produtos pelo nome
    @Query("SELECT * FROM product WHERE name LIKE '%' || :query || '%'")
    fun searchProducts(query: String): LiveData<List<Product>>

    // Exclui todos os produtos
    @Query("DELETE FROM product")
    fun deleteAll(): Int
}
