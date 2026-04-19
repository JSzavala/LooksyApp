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
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaAgregarProducto( viewModel: Funcionalidad_subirProducto = Funcionalidad_subirProducto(),
    onVolver: () -> Unit,
    navController: NavHostController) {
    val scrollState = rememberScrollState()
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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Detalles de la prenda", style = MaterialTheme.typography.bodySmall)

            OutlinedTextField(
                value = viewModel.nombrePrenda,
                onValueChange = { viewModel.nombrePrenda = it },
                label = { Text("Nombre de la prenda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.precioPrenda,
                onValueChange = { viewModel.precioPrenda = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Column {
                Text("Talla disponible:", style = MaterialTheme.typography.bodyLarge)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tallas.forEach { talla ->
                        // Comprobamos si la talla ya existe en la lista del ViewModel
                        val estaSeleccionada = viewModel.tallasSeleccionadas.contains(talla)

                        FilterChip(
                            selected = estaSeleccionada,
                            onClick = {
                                if (estaSeleccionada) {
                                    // Si ya estaba, la removemos (deseleccionar)
                                    viewModel.tallasSeleccionadas.remove(talla)
                                } else {
                                    // Si no estaba, la agregamos
                                    viewModel.tallasSeleccionadas.add(talla)
                                }
                            },
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
                value = viewModel.descripcionPrenda,
                onValueChange = { viewModel.descripcionPrenda = it },
                label = { Text("Descripción de la prenda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
                    .clickable { /* Aquí se abrira la galeria */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color.Gray
                    )
                    Text("Toca para añadir el artículo", color = Color.Gray)
                }
            }

            Button(
                onClick = { viewModel.publicar(navController) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF83B766),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Publicar Producto")
            }
        }
    }
}
/*@Composable
fun PantallaAgregarProducto(viewModel: Funcionalidad_subirProducto = Funcionalidad_subirProducto()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // El valor del TextField ahora viene del ViewModel
        OutlinedTextField(
            value = viewModel.nombrePrenda,
            onValueChange = { viewModel.nombrePrenda = it },
            label = { Text("Nombre de la prenda") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = viewModel.precioPrenda,
            onValueChange = { viewModel.precioPrenda = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = { viewModel.publicar() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF83B766),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Publicar Producto")
        }
    }
}*/

/*@Preview
@Composable
fun VistaPreviaAgregar() {
    VistaAgregarProducto(onVolver = {})
}*/
