package com.example.Looksy.SubirProducto

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaAgregarProducto( 
    viewModel: Funcionalidad_subirProducto = Funcionalidad_subirProducto(),
    onVolver: () -> Unit,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    val tallas = listOf("CH", "M", "G", "XL")
    
    // Validación para habilitar el botón de publicar: ningún campo vacío y tallas seleccionadas
    val esValido = viewModel.nombrePrenda.isNotBlank() &&
                   viewModel.precioPrenda.isNotBlank() &&
                   viewModel.stockPrenda.isNotBlank() &&
                   viewModel.descripcionPrenda.isNotBlank() &&
                   viewModel.tallasSeleccionadas.isNotEmpty()

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
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Detalles de la prenda", style = MaterialTheme.typography.bodySmall)

            OutlinedTextField(
                value = viewModel.nombrePrenda,
                onValueChange = { if (it.length <= 50) viewModel.nombrePrenda = it },
                label = { Text("Nombre de la prenda") },
                leadingIcon = {
                    Icon(Icons.Default.Label, contentDescription = null, tint = Color(0xFF6750A4))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    Text(text = "${viewModel.nombrePrenda.length}/50")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.precioPrenda,
                onValueChange = { input ->
                    // Permite números y hasta dos decimales usando Regex
                    if (input.isEmpty() || input.matches(Regex("""^\d*\.?\d{0,2}$"""))) {
                        viewModel.precioPrenda = input
                    }
                },
                label = { Text("Precio") },
                leadingIcon = {
                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = Color(0xFF6750A4))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )
            
            OutlinedTextField(
                value = viewModel.stockPrenda,
                onValueChange = { if (it.all { char -> char.isDigit() }) viewModel.stockPrenda = it },
                label = { Text("Stock") },
                leadingIcon = {
                    Icon(Icons.Default.Inventory, contentDescription = null, tint = Color(0xFF6750A4))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            
            Column {
                Text("Talla disponible:", style = MaterialTheme.typography.bodyLarge)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tallas.forEach { talla ->
                        val estaSeleccionada = viewModel.tallasSeleccionadas.contains(talla)

                        FilterChip(
                            selected = estaSeleccionada,
                            onClick = {
                                if (estaSeleccionada) {
                                    viewModel.tallasSeleccionadas.remove(talla)
                                } else {
                                    viewModel.tallasSeleccionadas.add(talla)
                                }
                            },
                            label = { Text(talla) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF6750A4),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            OutlinedTextField(
                value = viewModel.descripcionPrenda,
                onValueChange = { if (it.length <= 80) viewModel.descripcionPrenda = it },
                label = { Text("Descripción de la prenda") },
                leadingIcon = {
                    Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFF6750A4))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    Text(text = "${viewModel.descripcionPrenda.length}/80")
                }
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
                onClick = { if (esValido) viewModel.publicar(navController) },
                enabled = esValido,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (esValido) Color(0xFF6750A4) else Color.Gray,
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

@Preview(showBackground = true)
@Composable
fun VistaPreviaAgregar() {
    val viewModelFalso = Funcionalidad_subirProducto()
    MaterialTheme { 
        VistaAgregarProducto(
            viewModel = viewModelFalso,
            onVolver = {},
            navController = androidx.navigation.compose.rememberNavController()
        )
    }
}
