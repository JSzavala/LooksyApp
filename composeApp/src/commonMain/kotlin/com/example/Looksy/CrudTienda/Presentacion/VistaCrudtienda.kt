package com.example.Looksy.CrudTienda.Presentacion

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import com.example.Looksy.SubirProducto.VistaAgregarProducto


@Composable
fun VistaCrudtienda(navController: NavHostController) {
    var listaProductos by remember { mutableStateOf(listOf<ProductoTienda>()) }
    val verdeLooksy = Color(0xFF81C748)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "MIS PRODUCTOS:",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp).align(Alignment.CenterHorizontally)
            )

            if (listaProductos.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            onClick ={
                                navController.navigate("agregar");
                                /*listaProductos = listaProductos + ProductoTienda(
                                    listaProductos.size,
                                    "Producto Nuevo",
                                    "$0.00",
                                    Color.LightGray
                                )*/
                            },
                            modifier = Modifier.size(160.dp),
                            shape = CircleShape,
                            color = verdeLooksy
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.padding(40.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Publicar productos",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(listaProductos) { producto ->
                        CardProducto(producto, verdeLooksy)
                    }
                }
            }
        }

        if (listaProductos.isNotEmpty()) {
            FloatingActionButton(
                onClick = {
                    listaProductos = listaProductos + ProductoTienda(
                        listaProductos.size,
                        "Nuevo Item",
                        "$0.00",
                        Color.Gray
                    )
                },
                containerColor = verdeLooksy,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    }
}

@Composable
fun CardProducto(producto: ProductoTienda, colorBoton: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
            Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(producto.colorPlaceholder, RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(producto.nombre, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Text(producto.precio, color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(
                onClick = { /* Acción modificar */ },
                colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                modifier = Modifier.weight(1f).height(32.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Modificar", fontSize = 10.sp, color = Color.White)
            }
            Button(
                onClick = { /* Acción eliminar */ },
                colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                modifier = Modifier.weight(1f).height(32.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Eliminar", fontSize = 10.sp, color = Color.White)
            }
        }
    }
}