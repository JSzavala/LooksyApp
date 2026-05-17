package com.example.Looksy.Ajustes.Presentacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaAjustes(
    viewModel: ModeloAjustes,
    onNavigateBack: () -> Unit,
    onNavigateToEditarPerfil: () -> Unit,
    onNavigateToCambiarContrasena: () -> Unit,
    onNavigateToDireccionDespacho: () -> Unit // Nueva navegación
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes de la Tienda", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            item {
                VerificationStatusCard(
                    isVerified = state.isVerified,
                    rfc = state.rfc,
                    onVerifyClick = {
                        viewModel.registrarRfcYVerificar("LOOKSY123456RFC")
                    }
                )
            }

            item { SettingsCategoryHeader(title = "Cuenta del Administrador") }
            item {
                SettingsClickableItem(
                    icon = Icons.Default.Person,
                    title = "Datos del Perfil",
                    subtitle = state.nombreTienda
                ) {
                    onNavigateToEditarPerfil()
                }
            }
            item {
                SettingsClickableItem(
                    icon = Icons.Default.Email,
                    title = "Correo de Acceso",
                    subtitle = state.correoAdmin
                ) { /* Lógica para editar correo */ }
            }
            item {
                SettingsClickableItem(
                    icon = Icons.Default.Lock,
                    title = "Seguridad",
                    subtitle = "Cambiar contraseña y accesos"
                ) {
                    onNavigateToCambiarContrasena()
                }
            }

            item { SettingsCategoryHeader(title = "Información de la Tienda (Pública)") }
            item {
                SettingsClickableItem(
                    icon = Icons.Default.LocationOn,
                    title = "Dirección de Despacho",
                    subtitle = state.direccionDespacho // Ahora muestra la dirección actual
                ) {
                    onNavigateToDireccionDespacho() // Navega a la nueva pantalla
                }
            }

            item { SettingsCategoryHeader(title = "Preferencias del Sistema") }
            item {
                SettingsSwitchItem(
                    icon = Icons.Default.Notifications,
                    title = "Alertas de Servidor e Inventario",
                    checked = state.alertasServidorEnabled,
                    onCheckedChange = { nuevoEstado ->
                        viewModel.toggleAlertasServidor(nuevoEstado)
                    }
                )
            }
            item {
                SettingsSwitchItem(
                    icon = Icons.Default.Settings,
                    title = "Modo Oscuro",
                    checked = state.modoOscuroEnabled,
                    onCheckedChange = { nuevoEstado ->
                        viewModel.toggleModoOscuro(nuevoEstado)
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun VerificationStatusCard(
    isVerified: Boolean,
    rfc: String,
    onVerifyClick: () -> Unit
) {
    val containerColor = if (isVerified) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
    val contentColor = if (isVerified) Color(0xFF2E7D32) else Color(0xFFE65100)
    val icon = if (isVerified) Icons.Default.CheckCircle else Icons.Default.Warning

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isVerified) "Negocio Confiable Verificado" else "Sello de Confianza Pendiente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isVerified) "Tu RFC ($rfc) ha sido validado con éxito. Los clientes ahora ven el escudo de confianza en tu perfil de Looksy."
                else "Agrega tu RFC y la documentación de tu negocio. Al verificar que tu tienda existe, activaremos el sello de confianza para tus clientes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!isVerified) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onVerifyClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Configurar RFC y Verificar")
                }
            }
        }
    }
}

@Composable
fun SettingsCategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsClickableItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Ir",
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
