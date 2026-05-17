package com.example.Looksy.Ajustes.Presentacion

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaEditarPerfil(
    viewModel: ModeloAjustes,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state
    var nombre by remember { mutableStateOf(state.nombreTienda) }
    var correo by remember { mutableStateOf(state.correoAdmin) }
    var descripcion by remember { mutableStateOf(state.descripcion) }

    val esValido = nombre.isNotBlank() && correo.isNotBlank() && descripcion.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil", fontWeight = FontWeight.Bold) },
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
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la Tienda") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = descripcion,
                onValueChange = { if (it.length <= 100) descripcion = it },
                label = { Text("Descripción de la Tienda") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    Text(
                        text = "${descripcion.length}/100",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo del Administrador") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    if (esValido) {
                        viewModel.actualizarNombreTienda(nombre)
                        viewModel.actualizarCorreoAdmin(correo)
                        viewModel.actualizarDescripcion(descripcion)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = esValido
            ) {
                Text("Guardar Cambios")
            }
            
            if (!esValido) {
                Text(
                    text = "* Todos los campos son obligatorios",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
