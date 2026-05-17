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

    val esValido = nuevaContrasena.isNotBlank() && 
                   confirmarContrasena.isNotBlank() && 
                   nuevaContrasena.length <= 10 && 
                   confirmarContrasena.length <= 10

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
                    if (it.length <= 10) {
                        nuevaContrasena = it
                        error = null
                    }
                },
                label = { Text("Nueva Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    Text("${nuevaContrasena.length}/10")
                },
                isError = nuevaContrasena.length > 10
            )

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { 
                    if (it.length <= 10) {
                        confirmarContrasena = it
                        error = null
                    }
                },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    Text("${confirmarContrasena.length}/10")
                },
                isError = confirmarContrasena.length > 10
            )

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (nuevaContrasena == confirmarContrasena) {
                        viewModel.actualizarContraseña(nuevaContrasena)
                        onNavigateBack()
                    } else {
                        error = "Las contraseñas no coinciden"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = esValido
            ) {
                Text("Actualizar Contraseña")
            }
        }
    }
}
