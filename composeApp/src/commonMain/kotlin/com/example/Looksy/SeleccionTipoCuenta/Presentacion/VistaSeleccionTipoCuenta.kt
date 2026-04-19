package com.example.Looksy.SeleccionTipoCuenta.Presentacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun VistaSeleccionTipoCuenta(
    onVolver: () -> Unit,
    onSeleccionar: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Selecciona tipo de cuenta")

        Button(onClick = { onVolver() }) {
            Text("Volver")
        }

        Text(
            text = "Tipo de cuenta",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSeleccionar("tienda") },
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Text(
                text = "Cuenta Tienda",
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSeleccionar("comprador") },
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Text(
                text = "Cuenta Comprador",
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}