package com.example.Looksy.Ajustes.Presentacion

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaCambiarContrasena(
    viewModel: ModeloAjustes,
    onNavigateBack: () -> Unit
) {
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cambiar Contraseña", fontWeight = FontWeight.Bold) },
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
                value = nuevaContrasena,
                onValueChange = { 
                    nuevaContrasena = it
                    error = null
                },
                label = { Text("Nueva Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { 
                    confirmarContrasena = it
                    error = null
                },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (nuevaContrasena.isEmpty()) {
                        error = "La contraseña no puede estar vacía"
                    } else if (nuevaContrasena == confirmarContrasena) {
                        viewModel.actualizarContraseña(nuevaContrasena)
                        onNavigateBack()
                    } else {
                        error = "Las contraseñas no coinciden"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Contraseña")
            }
        }
    }
}
