package com.example.Looksy.VistaProducto.Presentacion


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VistaProducto(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen principal (simulada con color)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(producto.imagenColor))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre y descripción
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Material: ${producto.material}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Colores disponibles (círculos)
            Row {
                producto.colores.forEach { colorName ->
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                            .background(
                                when (colorName) {
                                    "Rojo" -> Color.Red
                                    "Amarillo" -> Color.Yellow
                                    "Blanco" -> Color.White
                                    "Rosa" -> Color.Magenta
                                    else -> Color.Gray
                                },
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { /* Tu acción aquí */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(171,174,128), // Color en Hexadecimal (Púrpura)
                        contentColor = Color.Black          // Color del texto
                    )
                ) {
                    Text("Comprar ahora")
                }
                OutlinedButton(onClick = { /* Acción añadir al carrito */ }) {
                    Text("Añadir al carrito")
                }
            }
        }
    }
}
