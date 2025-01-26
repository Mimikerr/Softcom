package com.example.softcom.ui.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softcom.R
import com.example.softcom.data.model.Product
import com.softcom.utils.SampleData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, onProductClick: (Product) -> Unit) {
    // Estado para armazenar a categoria selecionada
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val productsByCategory = SampleData.productsByCategory
    val filteredProducts = selectedCategory?.let { category ->
        mapOf(category to productsByCategory[category].orEmpty())
    } ?: productsByCategory // Se nenhuma categoria for selecionada, mostre todos os produtos.

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0xFFFF5722),
            darkIcons = false
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(150.dp),
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pet Friends Accessories",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(55.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFFF5722))
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Categorias com clique
            CategoriesSection(
                categories = productsByCategory.keys.toList(),
                selectedCategory = selectedCategory,
                onCategoryClick = { category ->
                    selectedCategory = if (selectedCategory == category) null else category
                }
            )

            // Produtos filtrados por categoria
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                filteredProducts.forEach { (category, products) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(products) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = { onProductClick(product) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(text = "O que você procura?")
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Buscar",
                modifier = Modifier.height(150.dp)
            )
        },
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}



@Composable
fun CategoriesSection(
    categories: List<String>, 
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit
) {
    val categories = listOf(
        "Camas" to R.drawable.ic_cama,
        "Brinquedos" to R.drawable.ic_brinquedos,
        "Comedouros" to R.drawable.ic_comedouro,
        "Casinhas" to R.drawable.ic_casa
    )
    
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        categories.forEach { (category, iconRes) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            if (selectedCategory == category) Color.LightGray else Color(0xFFFF5722),
                            CircleShape
                        )
                        .clickable { onCategoryClick(category) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = category,
                        tint = if (selectedCategory == category) Color.Black else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = category,
                    fontSize = 12.sp,
                    color = if (selectedCategory == category) Color(0xFFFF5722) else Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSection(category: String, products: List<Product>, onProductClick: (Product) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = { onProductClick(product) }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(300.dp) // Ajuste de largura do produto
            .padding(8.dp)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(8.dp) // Cantos arredondados na borda
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Corrigido
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centraliza os itens no eixo horizontal
        ) {
            // Imagem do Produto
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp) // Borda arredondada ao redor da imagem
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes), // Substitua por seu recurso de imagem
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Ajusta a imagem ao tamanho do box
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nome do Produto
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Preço Original e Promocional
            Column(modifier = Modifier.padding(top = 4.dp)) {
                if (product.originalPrice != null) {
                    Text(
                        text = "${((1 - (product.price / product.originalPrice)) * 100).toInt()}% OFF",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .background(Color(0xFF4CAF50), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }else {
                    Spacer(modifier = Modifier.height(30.dp)) // Espaço reservado
                }
                Text(
                    text = "Por R$ ${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF388E3C) // Verde para o preço promocional
                )
            }
        }
    }
}




@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text("Home") },
            selected = false, // Adicione lógica de seleção com base na rota atual
            onClick = {
                navController.navigate("home") // Navegar para a tela Home
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_orders),
                    contentDescription = "Carrinho",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text("Pedidos") },
            selected = false,
            onClick = {
                navController.navigate("cart") // Navegar para a tela do carrinho
            }
        )
    }
}
