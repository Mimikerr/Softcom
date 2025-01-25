package com.example.softcom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.softcom.ui.home.HomeScreen
import android.content.Context
import androidx.room.Room
import com.example.softcom.data.database.AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(onProductClick = { product ->
                // TODO: Navegar para detalhes do produto
            })
        }
    }
}

object DatabaseInstance {
    lateinit var database: AppDatabase
        private set

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "softcom_database"
        ).build()
    }
}