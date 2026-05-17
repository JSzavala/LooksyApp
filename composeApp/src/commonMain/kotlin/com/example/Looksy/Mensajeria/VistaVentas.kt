package com.example.Looksy.Mensajeria

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun VistaVentas(navController: NavHostController) {

    var pestaña by remember { mutableStateOf("realizadas") }

    val morado = Color(0xFF7E57C2)
    val moradoClaro = Color(0xFFEDE7F6)

    val ventas = VentasRepository.ventas

    val realizadasCount = ventas.count {
        it.estado == "Completada"
    }

    val pendientesCount = ventas.count {
        it.estado == "Pendiente"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp)
    ) {

        Text(
            "Mis Ventas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = { pestaña = "realizadas" },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (pestaña == "realizadas") morado else moradoClaro
                )
            ) {
                Text("Realizadas ($realizadasCount)")
            }

            Button(
                onClick = { pestaña = "pendientes" },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (pestaña == "pendientes") morado else moradoClaro
                )
            ) {
                Text("Pendientes ($pendientesCount)")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ventas.filter {
                if (pestaña == "realizadas")
                    it.estado == "Completada"
                else
                    it.estado == "Pendiente"
            }) { venta ->

                Card(
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            venta.id,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            venta.producto,
                            fontWeight = FontWeight.Bold
                        )

                        Text("Cliente: ${venta.cliente}")
                        Text(venta.fechaCompra)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            venta.precio,
                            color = morado,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                navController.navigate("detalleVenta/${venta.id}")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = morado
                            )
                        ) {
                            Icon(
                                Icons.Default.RemoveRedEye,
                                null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ver detalles")
                        }
                    }
                }
            }
        }
    }
}

