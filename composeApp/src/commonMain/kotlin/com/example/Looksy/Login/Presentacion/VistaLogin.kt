package com.example.Looksy.Login.Presentacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun VistaLogin(
    onLoginSuccess: () -> Unit,
    onCreateAccount: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var mostrarContrasena by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LOOKSY",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 48.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = {
                usuario = it
                error = null
            },
            label = { Text("Usuario") },
            shape = RoundedCornerShape(16.dp), // Bordes redondeados
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                error = null
            },
            label = { Text("Contraseña") },
            shape = RoundedCornerShape(16.dp),
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
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            singleLine = true
        )

        Box(modifier = Modifier.height(24.dp)) {
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (usuario.trim() == "deportiva" && contrasena.trim() == "1234") {
                    onLoginSuccess()
                } else {
                    error = "Credenciales incorrectas"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp)) // espacio

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "¿No tienes cuenta? ", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Regístrate",
                modifier = Modifier.clickable { onCreateAccount() },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}


