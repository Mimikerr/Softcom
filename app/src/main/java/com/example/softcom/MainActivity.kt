package com.example.softcom

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Room
import com.example.softcom.data.database.AppDatabase
import com.example.softcom.data.model.CartItem
import com.example.softcom.ui.cart.CartScreen
import com.example.softcom.ui.home.HomeScreen
import com.example.softcom.ui.product.ProductDetailsScreen
import com.softcom.utils.SampleData

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Declare explicitamente o tipo de `currentCartItems`
            val currentCartItems: SnapshotStateList<CartItem> = mutableStateListOf()
            val navController = rememberNavController()
            AppNavHost(navController = navController, currentCartItems = currentCartItems)
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun AppNavHost(navController: NavHostController, currentCartItems: SnapshotStateList<CartItem>) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Tela Home
        composable("home") {
            HomeScreen(
                navController = navController, // Passa o NavController
                onProductClick = { product ->
                    navController.navigate("productDetails/${product.id}")
                }
            )
        }

        // Tela de detalhes do produto
        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            val product = SampleData.productsByCategory.values.flatten()
                .firstOrNull { it.id == productId }

            product?.let {
                ProductDetailsScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { addedProduct, quantity, observation ->
                        val existingItem = currentCartItems.find {
                            it.product.id == addedProduct.id && it.observation == observation
                        }
                        if (existingItem != null) {
                            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
                            currentCartItems[currentCartItems.indexOf(existingItem)] = updatedItem
                        } else {
                            val newItem = CartItem(
                                product = addedProduct,
                                quantity = quantity,
                                observation = observation
                            )
                            currentCartItems.add(newItem)
                        }
                    }
                )
            } ?: navController.popBackStack()
        }

        // Tela do carrinho
        composable("cart") {
            CartScreen(
                cartItems = currentCartItems,
                onBackClick = { navController.popBackStack() },
                onCheckout = { /* Implementar lógica de finalização da compra */ }
            )
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
