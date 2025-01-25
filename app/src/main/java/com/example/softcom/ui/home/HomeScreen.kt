package com.example.softcom.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onProductClick: (Product) -> Unit) {
    val productsByCategory = SampleData.productsByCategory

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pet Friends Accessories",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFFF5722))
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Barra de pesquisa
            SearchBar()

            // Categorias
            CategoriesSection()

            // Produtos por categoria
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                productsByCategory.forEach { (category, products) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(products) { product ->
                        ProductCard(product = product, onClick = { onProductClick(product) })
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search), // Ícone de busca
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp) // Ajuste o tamanho aqui (exemplo: 20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "O que você procura?",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



@Composable
fun CategoriesSection() {
    val categories = listOf(
        "Camas" to R.drawable.ic_cama, // Substitua pelos seus ícones reais
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
                        painter = painterResource(id = iconRes), // Ícone específico
                        contentDescription = "$category icon",
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
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Correção aqui
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Exibir a imagem do produto
            Image(
                painter = painterResource(id = R.drawable.ic_placeholder), // Substitua pelo recurso da imagem correta
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (product.originalPrice != null) {
                    Text(
                        text = "De R$ ${"%.2f".format(product.originalPrice)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "Por R$ ${"%.2f".format(product.price)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}



@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_home),modifier = Modifier.size(20.dp), contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { /* Navegar para Home */ }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_orders),modifier = Modifier.size(20.dp), contentDescription = "Pedidos") },
            label = { Text("Pedidos") },
            selected = false,
            onClick = { /* Navegar para Pedidos */ }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_more),modifier = Modifier.size(20.dp), contentDescription = "Mais") },
            label = { Text("Mais") },
            selected = false,
            onClick = { /* Navegar para Mais */ }
        )
    }
}

