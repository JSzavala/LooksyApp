package com.example.Looksy.CrearCuenta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaComprador(
    onVolver: () -> Unit,
    onGuardar: () -> Unit = {}
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var apellidoP by remember{ mutableStateOf("") }
    var apellidoM by remember{ mutableStateOf("") }


    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    var rol by remember { mutableStateOf("cliente") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Perfil", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Crea tu usuario",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        CustomTextField(
            value = usuario,
            onValueChange = { usuario = it
                              error = null},
            label = "Nombre de Usuario",
            icon = Icons.Default.AlternateEmail
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it
                              error = null},
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = if (mostrarContrasena)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (mostrarContrasena)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff

                IconButton(onClick = {
                    mostrarContrasena = !mostrarContrasena
                }) {
                    Icon(imageVector = icon, contentDescription = "Mostrar contraseña")
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

        CustomTextField(
            value = nombre,
            onValueChange = { nombre = it
                              error = null},
            label = "Nombre(s)",
            icon = Icons.Default.Person
        )

        CustomTextField(
            value = apellidoP,
            onValueChange = { apellidoP = it
                              error = null},
            label = "Apellido Paterno",
            icon = Icons.Default.Badge
        )

        CustomTextField(
            value = apellidoM,
            onValueChange = { apellidoM = it
                              error = null},
            label = "Apellido Materno",
            icon = Icons.Default.Badge
        )

        CustomTextField(
            value = correo,
            onValueChange = { correo = it
                              error = null},
            label = "Correo electrónico",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )

        CustomTextField(
            value = direccion,
            onValueChange = { direccion = it
                              error = null},
            label = "Dirección",
            icon = Icons.Default.Home
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
                if (usuario.isEmpty() || nombre.isEmpty() || apellidoP.isEmpty() ||
                    correo.isEmpty() || contrasena.isEmpty()) {
                    error = "Por favor, completa los campos obligatorios"
                } else if (!correo.contains("@")) {
                    error = "El correo no es válido"
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
            Text("Registrarme", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
    }
}