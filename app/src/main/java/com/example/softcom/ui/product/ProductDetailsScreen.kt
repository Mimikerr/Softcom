package com.example.softcom.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.softcom.data.model.Product
import androidx.compose.material3.TextFieldDefaults



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(product: Product, onBackClick: () -> Unit, onAddToCart: (Product, Int, String) -> Unit) {
    var quantity by remember { mutableIntStateOf(1) }
    var observation by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes do Produto",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFFF5722))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Imagem do Produto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes), // Substituir por sua lógica para obter imagens
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Promoção
            if (product.originalPrice != null) {
                Text(
                    text = "${((1 - (product.price / product.originalPrice)) * 100).toInt()}% OFF",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .background(Color(0xFF4CAF50), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nome do Produto
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )

            // Preço do Produto
            if (product.originalPrice != null) {
                Text(
                    text = "De R$ ${"%.2f".format(product.originalPrice)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Text(
                text = "Por R$ ${"%.2f".format(product.price)}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição do Produto
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Observação
            OutlinedTextField(
                value = observation,
                onValueChange = { observation = it },
                label = { Text("Observações") },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 5,
                shape = RoundedCornerShape(8.dp) // Mantendo apenas o formato arredondado
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Controles de Quantidade e Botão de Adicionar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Controle de quantidade
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Diminuir")
                    }
                    Text(text = quantity.toString(), style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = { quantity++ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Aumentar")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botão de Adicionar
                Button(
                    onClick = { onAddToCart(product, quantity, observation) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Adicionar por R$ ${"%.2f".format(product.price * quantity)}",
                        color = Color.White
                    )
                }
            }
        }
    }
}
