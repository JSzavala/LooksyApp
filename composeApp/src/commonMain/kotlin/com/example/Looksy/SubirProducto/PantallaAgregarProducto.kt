package com.example.Looksy.SubirProducto

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaAgregarProducto(onVolver: () -> Unit) {
    // Estados para los campos de texto
    var nombrePrenda by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenSeleccionada by remember { mutableStateOf<Any?>(null) }
    var tallaSeleccionada by remember { mutableStateOf("") }
    val tallas = listOf("CH", "M", "G", "XL")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subir producto") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Detalles de la prenda", style = MaterialTheme.typography.bodySmall)

            OutlinedTextField(
                value = nombrePrenda,
                onValueChange = { nombrePrenda = it },
                label = { Text("Nombre de la prenda") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                Text("Talla disponible:", style = MaterialTheme.typography.bodyLarge)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tallas.forEach { talla ->
                        FilterChip(
                            selected = tallaSeleccionada == talla,
                            onClick = { tallaSeleccionada = talla },
                            label = { Text(talla) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF83B766), // Tu verde
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
                    .clickable { /* Aquí se abriría la galería después */ },
                contentAlignment = Alignment.Center
            ) {
                if (imagenSeleccionada == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto, // Necesitas importar Icons.Default.AddAPhoto
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Text("Toca para añadir el artículo", color = Color.Gray)
                    }
                } else {
                    // Aquí se mostraría la imagen una vez seleccionada
                }
            }

            Button(
                onClick = { /* Aquí conectarás con tu backend de Node.js después */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF83B766), // Color de fondo (ejemplo: un rosa pastel)
                    contentColor = Color.White// Color del texto (ejemplo: negro)
                ),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Publicar Producto")
            }
        }
    }
}

@Preview
@Composable
fun VistaPreviaAgregar() {
    VistaAgregarProducto(onVolver = {})
}
