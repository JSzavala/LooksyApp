package com.example.Looksy.Ajustes.Presentacion

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaDireccionDespacho(
    viewModel: ModeloAjustes,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state
    var nuevaDireccion by remember { mutableStateOf(state.direccionDespacho) }
    
    val esValido = nuevaDireccion.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dirección de Despacho", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Configura la dirección desde la cual se enviarán tus productos.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = nuevaDireccion,
                onValueChange = { nuevaDireccion = it },
                label = { Text("Dirección completa") },
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: Av. Principal 123, Ciudad, Estado") },
                isError = !esValido && nuevaDireccion.isNotEmpty()
            )

            if (!esValido) {
                Text(
                    text = "* La dirección no puede estar vacía",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (esValido) {
                        viewModel.actualizarDireccionDespacho(nuevaDireccion)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = esValido,
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Guardar Dirección")
            }
        }
    }
}
