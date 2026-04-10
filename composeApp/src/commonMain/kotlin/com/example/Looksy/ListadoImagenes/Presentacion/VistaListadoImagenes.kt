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
                PostItem(
                    color = color,
                    onLongClick = {
                        mostrarMenu = true
                    }
                )
            }
        }
        if (mostrarMenu) {
            VistaMenuDesplazableInferior(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Opción 1")
                Text("Opción 2")
                Text("Opción 3")
            }
        }
    }
}