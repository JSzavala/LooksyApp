package com.example.Looksy.VistaProducto.Presentacion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun VistaListadoProductos() {
    val productos = remember {
        listOf(
            Producto(
                nombre = "Chamarra afelpada",
                descripcion = "Chamarra color marrón afelpada con gorro desmontable de peluche.",
                material = "Tela",
                colores = listOf("Rojo", "Amarillo", "Blanco", "Rosa"),
                imagenColor = 0xFFA0522D // Marrón
            ) ,
            Producto(
                    nombre = "Chamarra afelpada",
                    descripcion = "Chamarra color marrón afelpada con gorro desmontable de peluche.",
                    material = "Tela",
                    colores = listOf("Rojo", "Amarillo", "Blanco", "Rosa"),
                    imagenColor = 0xFFA0522D // Marrón
        )
            // Se pueden poner más productos aquí
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(productos) { producto ->
            VistaProducto(producto = producto)
        }
    }
}

data class Producto(
    val nombre: String,
    val descripcion: String,
    val material: String,
    val colores: List<String>,
    val imagenColor: Long
)
