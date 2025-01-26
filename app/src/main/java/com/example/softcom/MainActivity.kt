package com.example.softcom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.compose.runtime.Composable
import android.content.Context
import androidx.room.Room
import com.example.softcom.data.database.AppDatabase
import com.example.softcom.data.model.Product
import com.example.softcom.ui.home.HomeScreen
import com.example.softcom.ui.product.ProductDetailsScreen
import com.softcom.utils.SampleData




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar banco de dados
        DatabaseInstance.init(this)

        setContent {
            val navController = rememberNavController()
            AppNavHost(navController = navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Tela Home
        composable("home") {
            HomeScreen { product ->
                navController.navigate("productDetails/${product.id}")
            }
        }

        // Tela de Detalhes do Produto
        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            val product = SampleData.productsByCategory.values.flatten()
                .firstOrNull { it.id == productId }

            if (product != null) {
                ProductDetailsScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { _, _, _ -> /* Lógica do carrinho */ }
                )
            } else {
                // Caso o produto não seja encontrado, volte para a tela inicial
                navController.popBackStack()
            }
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
