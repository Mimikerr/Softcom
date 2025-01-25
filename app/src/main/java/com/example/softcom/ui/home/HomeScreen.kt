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
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onProductClick: (Product) -> Unit) {
    val productsByCategory = SampleData.productsByCategory
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
            BottomNavigationBar()
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategoriesSection()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                productsByCategory.forEach { (category, products) ->
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
                contentDescription = "Buscar"
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
fun CategoriesSection() {
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
                        .background(Color(0xFFFF5722), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = category,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = category,
                    fontSize = 12.sp,
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
                color = Color.LightGray,
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
                        color = Color.LightGray,
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
                        text = "De R$ ${product.originalPrice}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            textDecoration = TextDecoration.LineThrough
                        ),
                        color = Color.Gray
                    )
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
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text(text = "Home") },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_orders),
                    contentDescription = "Pedidos",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text(text = "Pedidos") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "Mais",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text(text = "Mais") },
            selected = false,
            onClick = { }
        )
    }
}
