package com.example.Looksy.ListadoImagenes.Presentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Looksy.Buscador.Presentacion.VistaBarraBusqueda
import com.example.Looksy.MenuDesplazableInferior.Presentacion.VistaMenuDesplazableInferior

@Composable
fun VistaListadoImagenes() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var mostrarMenu by remember { mutableStateOf(false) }
    val placeholderImages = remember {
        listOf(Color.Red, Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta, Color.Cyan)
    }

    // Usamos Box para que el menú pueda aparecer "encima" de la lista
    Box(modifier = Modifier.fillMaxSize()) {
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(placeholderImages) { color ->
                    PostItem(
                        color = color,
                        onClick = {
                            mostrarMenu = true // Esto ahora funcionará visualmente
                        }
                    )
                }
            }
        }

        // El menú se dibuja al final para que quede por encima de todo
        if (mostrarMenu) {
            VistaMenuDesplazableInferior(
                onDismiss = { mostrarMenu = false },
                modifier = Modifier.align(Alignment.BottomCenter) // Alineado abajo
            ) {
                Text("Opción 1", modifier = Modifier.padding(16.dp))
                Text("Opción 2", modifier = Modifier.padding(16.dp))
                Text("Opción 3", modifier = Modifier.padding(16.dp))
            }
        }
    }
}