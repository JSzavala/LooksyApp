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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.Looksy.CrudTienda.Datos.ProductoRepository
import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import com.example.Looksy.SubirProducto.VistaAgregarProducto
import androidx.compose.material.icons.filled.Search
import com.example.Looksy.Ajustes.Presentacion.ModeloAjustes


@Composable
fun VistaCrudtienda(
    navController: NavHostController,
    viewModelAjustes: ModeloAjustes // Añadido para reflejar cambios de ajustes
) {

    val listaProductos by ProductoRepository.productos.collectAsState()
    val stateAjustes = viewModelAjustes.state // Obtenemos el estado de ajustes
    val verdeLooksy = MaterialTheme.colorScheme.primary
    var textoBusqueda by remember { mutableStateOf("") }

    val productosFiltrados = listaProductos.filter {
        it.nombre.contains(textoBusqueda, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Header tipo perfil
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(
                                2.dp,
                                color = Color.Black
                            ),
                            modifier = Modifier.size(70.dp),
                            color = Color.LightGray
                        ) {}

                        Spacer(modifier = Modifier.width(20.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "${listaProductos.size}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text("publicaciones", fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.width(30.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "121",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text("seguidores", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        stateAjustes.nombreTienda.ifBlank { "Mi Tienda" }, // Refleja el cambio de nombre
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        stateAjustes.descripcion.ifBlank { "Descripción de la tienda" }, // Refleja el cambio de descripción
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate("ajustes")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF0F0F0),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Configuración")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                }
            }

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = {
                    textoBusqueda = it
                },
                label = {
                    Text("Buscar producto")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),

                shape = RoundedCornerShape(16.dp),

                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                }
            )

            if (
                productosFiltrados.isEmpty()
                && textoBusqueda.isNotBlank()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron productos")
                }
            }
            // Grid de productos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productosFiltrados) { producto ->
                    CardProducto(
                        producto,
                        verdeLooksy,
                        navController
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("agregar")
            },
            containerColor = verdeLooksy,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, null)
        }
    }
}

@Composable
fun CardProducto(
    producto: ProductoTienda,
    colorBoton: Color,
    navController: NavHostController
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        producto.colorPlaceholder,
                        RoundedCornerShape(12.dp)
                    )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                producto.nombre,
                fontWeight = FontWeight.Bold
            )

            Text(
                producto.precio,
                color = Color(0xFF00A86B),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        navController.navigate(
                            "agregar?productoId=${producto.id}"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBoton
                    ),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Text(
                        "Modificar",
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = {
                        ProductoRepository.eliminarProducto(producto.id)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBoton
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Eliminar",
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
