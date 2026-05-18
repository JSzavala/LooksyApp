package com.example.Looksy.CrearCuenta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.Looksy.CrudTienda.Datos.ApiException
import com.example.Looksy.CrudTienda.Datos.AuthApiService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaComprador(
    onVolver: () -> Unit,
    onIrLogin: () -> Unit,
    onGuardar: () -> Unit = {}
) {
    val usuarioFocus = remember { FocusRequester() }
    val contrasenaFocus = remember { FocusRequester() }
    val nombreFocus = remember { FocusRequester() }
    val apellidoPFocus = remember { FocusRequester() }
    val apellidoMFocus = remember { FocusRequester() }
    val correoFocus = remember { FocusRequester() }
    val direccionFocus = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var apellidoP by remember { mutableStateOf("") }
    var apellidoM by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    var mostrarDialogoExito by remember { mutableStateOf(false) }

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
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
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
                onValueChange = { usuario = it; error = null },
                label = "Nombre de Usuario",
                icon = Icons.Default.AlternateEmail,
                focusRequester = usuarioFocus,
                nextFocusRequester = contrasenaFocus,
                focusManager = focusManager
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it; error = null },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = if (mostrarContrasena) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (mostrarContrasena) Icons.Default.Visibility
                    else Icons.Default.VisibilityOff
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(imageVector = icon, contentDescription = "Mostrar contraseña")
                    }
                },
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { nombreFocus.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .focusRequester(contrasenaFocus)
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

            CustomTextField(
                value = nombre,
                onValueChange = { nombre = it; error = null },
                label = "Nombre(s)",
                icon = Icons.Default.Person,
                focusRequester = nombreFocus,
                nextFocusRequester = apellidoPFocus,
                focusManager = focusManager
            )

            CustomTextField(
                value = apellidoP,
                onValueChange = { apellidoP = it; error = null },
                label = "Apellido Paterno",
                icon = Icons.Default.Badge,
                focusRequester = apellidoPFocus,
                nextFocusRequester = apellidoMFocus,
                focusManager = focusManager
            )

            CustomTextField(
                value = apellidoM,
                onValueChange = { apellidoM = it; error = null },
                label = "Apellido Materno",
                icon = Icons.Default.Badge,
                focusRequester = apellidoMFocus,
                nextFocusRequester = correoFocus,
                focusManager = focusManager
            )

            CustomTextField(
                value = correo,
                onValueChange = { correo = it; error = null },
                label = "Correo electrónico",
                icon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                focusRequester = correoFocus,
                nextFocusRequester = direccionFocus,
                focusManager = focusManager
            )

            CustomTextField(
                value = direccion,
                onValueChange = { direccion = it; error = null },
                label = "Dirección",
                icon = Icons.Default.Home,
                focusRequester = direccionFocus,
                focusManager = focusManager
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

            if (mostrarDialogoExito) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Registro exitoso") },
                    text = { Text("El usuario se registró correctamente.") },
                    confirmButton = {
                        Button(onClick = {
                            mostrarDialogoExito = false
                            onIrLogin()
                        }) { Text("Aceptar") }
                    }
                )
            }

            Button(
                onClick = {
                    if (usuario.isEmpty() || nombre.isEmpty() || apellidoP.isEmpty() ||
                        correo.isEmpty() || contrasena.isEmpty()
                    ) {
                        error = "Por favor, completa los campos obligatorios"
                    } else if (!correo.contains("@")) {
                        error = "El correo no es válido"
                    } else {
                        cargando = true
                        error = null
                        scope.launch {
                            try {
                                val nombreCompleto = "$nombre $apellidoP $apellidoM".trim()
                                AuthApiService.register(
                                    nombre = nombreCompleto,
                                    correo = correo.trim(),
                                    contrasena = contrasena.trim(),
                                    rol = "cliente"
                                )
                                mostrarDialogoExito = true
                            } catch (e: ApiException) {
                                error = e.message ?: "Error al registrar"
                            } catch (e: Exception) {
                                error = "Error de conexión: ${e.message}"
                            } finally {
                                cargando = false
                            }
                        }
                    }
                },
                enabled = !cargando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (cargando) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                else Text("Registrarme", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
