package com.example.looksy.presentacion.pantallas.imagenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.looksy.presentacion.pantallas.busqueda.componentes.VistaBarraBusqueda

@Composable
fun VistaListadoImagenes() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // Generamos una lista de "colores" para simular diferentes imágenes
    val placeholderImages = remember {
        listOf(Color.Red, Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta, Color.Cyan)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
    ) {
        VistaBarraBusqueda(
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            modifier = Modifier.fillMaxWidth()
        )

        // El Feed tipo Instagram
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp) // Espacio al final
        ) {
            items(placeholderImages) { color ->
                PostItem(color = color)
            }
        }
    }
}

@Composable
fun PostItem(color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
            // Cabecera del post (Avatar + Nombre)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Usuario_Looksy",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Imagen (Rectángulo de color)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // Altura fija para el feed
                    .background(color)
            )

            // Pie del post (Likes / Descripción)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "1,234 likes",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Esta es una descripción de ejemplo para el post de LooksyApp usando Compose Multiplatform.",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
                Text(
                    text = "Ver los 15 comentarios",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
