package com.softcom.utils

import com.example.softcom.R
import com.example.softcom.data.model.Product

object SampleData {
    val productsByCategory = mapOf(
        "Camas" to listOf(
            Product(
                id = 1,
                name = "Cama Londres Azul",
                description = "Uma cama confortável e estilosa para seu pet.",
                price = 266.31,
                originalPrice = 295.90,
                imageRes = R.drawable.produto2,
                category = "Camas"
            ),
            Product(
                id = 2,
                name = "Cama Luppet Quadrada Azul",
                description = "Cama quadrada perfeita para seu pet descansar.",
                price = 130.60,
                imageRes = R.drawable.produto1,
                category = "Camas"
            ),

            Product(
                id = 5,
                name = "Cama para pet",
                description = "Cama quadrada perfeita para seu pet descansar.",
                price = 130.60,
                originalPrice = 150.60,
                imageRes = R.drawable.produto3,
                category = "Camas"
            )            
        ),
        "Brinquedos" to listOf(
            Product(
                id = 3,
                name = "Brinquedo Mordedor",
                description = "Brinquedo seguro e divertido para morder.",
                price = 40.00,
                originalPrice = 50.00,
                imageRes = R.drawable.produto6,
                category = "Brinquedos"
            ),
            Product(
                id = 4,
                name = "Brinquedo Pelúcia Macaco",
                description = "Pelúcia em forma de macaco, ideal para brincar.",
                price = 20.00,
                imageRes = R.drawable.produto5,
                category = "Brinquedos"
            )
        )
    )
}
