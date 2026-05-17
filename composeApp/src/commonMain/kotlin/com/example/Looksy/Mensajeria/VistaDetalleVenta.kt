package com.example.Looksy.Mensajeria

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*


@Composable
fun VistaDetalleVenta(
    ventaId: String,
    onVolver: () -> Unit
) {

    val venta = VentasRepository.obtenerVentaPorId(ventaId)

    val morado = Color(0xFF7E57C2)
    val moradoClaro = Color(0xFFF3EEFF)

    venta?.let {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F7FC))
                .verticalScroll(rememberScrollState())
        ) {

            // HEADER
            Surface(
                color = moradoClaro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            "Detalles de la Venta",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = onVolver) {
                            Icon(
                                Icons.Default.Close,
                                null,
                                tint = morado
                            )
                        }
                    }

                    Text(
                        it.id,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ESTADO
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = moradoClaro
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 10.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        null,
                        tint = morado
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Orden ${it.estado}",
                        color = morado,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // PRODUCTO
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.Inventory,
                        null,
                        tint = morado,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            "PRODUCTO",
                            color = Color.Gray
                        )

                        Text(
                            it.producto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // PRECIO + CANTIDAD
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                InfoGrande(
                    "PRECIO TOTAL",
                    it.precio,
                    morado
                )

                InfoGrande(
                    "CANTIDAD",
                    "${it.cantidad}\nprendas",
                    Color(0xFF5E35B1)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoCard(
                "CLIENTE",
                it.cliente,
                Icons.Default.Person
            )

            InfoCard(
                "DIRECCIÓN DE ENTREGA",
                it.direccion,
                Icons.Default.LocationOn
            )

            InfoCard(
                "FECHA DE COMPRA",
                it.fechaCompra,
                Icons.Default.DateRange
            )

            InfoCard(
                "FECHA DE ENTREGA",
                it.fechaEntrega,
                Icons.Default.CheckCircle
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(58.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = morado
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(
                    "Cerrar",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun RowScope.InfoGrande(
    titulo: String,
    valor: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .height(130.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            color,
                            color.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(18.dp)
        ) {

            Column {
                Text(
                    titulo,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    valor,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        }
    }
}

@Composable
fun InfoCard(
    titulo: String,
    valor: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 8.dp
            ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(18.dp)
        ) {

            Icon(
                icono,
                null,
                tint = Color(0xFF7E57C2)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    titulo,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    valor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}