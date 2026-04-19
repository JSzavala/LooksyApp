package com.example.Looksy.CrearCuenta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.input.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaTienda(
    onVolver: () -> Unit,
    onGuardar: () -> Unit = {}
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var nombreTienda by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    var rol by remember { mutableStateOf("proveedor") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configura tu Tienda", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()), // Para evitar que el teclado tape los campos
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.Storefront,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Nueva tienda",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        CustomTextField(
            value = usuario,
            onValueChange = { usuario = it
                              error = null},
            label = "Nombre de Usuario",
            icon = Icons.Default.Person
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it
                              error = null},
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        CustomTextField(
            value = correo,
            onValueChange = { correo = it
                              error = null},
            label = "Correo electrónico",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

        CustomTextField(
            value = nombreTienda,
            onValueChange = { nombreTienda = it
                              error = null},
            label = "Nombre de la Tienda",
            icon = Icons.Default.Business
        )

        CustomTextField(
            value = direccion,
            onValueChange = { direccion = it
                              error = null},
            label = "Dirección física",
            icon = Icons.Default.LocationOn
        )

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombreTienda.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                    error = "Por favor, llena los campos obligatorios"
                } else if (!correo.contains("@")) {
                    error = "El formato del correo no es válido"
                } else {
                    error = null
                    onGuardar()
                    onVolver()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Crear Tienda", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
    }
}

// Componente reutilizable para los campos
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        singleLine = true
    )
}