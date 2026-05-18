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
import com.example.Looksy.CrudTienda.Datos.AuthManager
import com.example.Looksy.CrudTienda.Datos.TiendaInfo
import com.example.Looksy.CrudTienda.Datos.UsuarioInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaTienda(
    onVolver: () -> Unit,
    onIrLogin: () -> Unit,
    onGuardar: () -> Unit = {}
) {
    val nombreFocus = remember { FocusRequester() }
    val contrasenaFocus = remember { FocusRequester() }
    val correoFocus = remember { FocusRequester() }
    val direccionFocus = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    val maxLen = 50

    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var nombreTienda by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    var mostrarDialogoExito by remember { mutableStateOf(false) }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
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
                value = nombreTienda,
                onValueChange = { if (it.length <= maxLen) nombreTienda = it; error = null },
                label = "Nombre de la Tienda",
                icon = Icons.Default.Business,
                focusRequester = nombreFocus,
                nextFocusRequester = contrasenaFocus,
                focusManager = focusManager
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = { if (it.length <= maxLen) contrasena = it; error = null },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = if (mostrarContrasena) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (mostrarContrasena) Icons.Default.Visibility
                    else Icons.Default.VisibilityOff
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { correoFocus.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .focusRequester(contrasenaFocus)
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

            CustomTextField(
                value = correo,
                onValueChange = { if (it.length <= maxLen) correo = it; error = null },
                label = "Correo electrónico",
                icon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                focusRequester = correoFocus,
                nextFocusRequester = direccionFocus,
                focusManager = focusManager
            )

            CustomTextField(
                value = direccion,
                onValueChange = { if (it.length <= maxLen) direccion = it; error = null },
                label = "Dirección física",
                icon = Icons.Default.LocationOn,
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
                    text = { Text("La tienda se registró correctamente.") },
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
                    if (nombreTienda.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || direccion.isEmpty()) {
                        error = "Por favor, llena los campos obligatorios"
                    } else if (nombreTienda.length < 5) {
                        error = "El nombre de la tienda debe tener al menos 5 caracteres"
                    } else if (direccion.length < 10) {
                        error = "La dirección debe tener al menos 10 caracteres"
                    } else if (contrasena.length < 8) {
                        error = "La contraseña debe tener al menos 8 caracteres"
                    } else if (nombreTienda.length > maxLen || correo.length > maxLen || contrasena.length > maxLen || direccion.length > maxLen) {
                        error = "Los campos no pueden exceder los $maxLen caracteres"
                    } else if (!correo.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
                        error = "El formato del correo no es válido"
                    } else {
                        cargando = true
                        error = null
                        scope.launch {
                            try {
                                val authResponse = AuthApiService.register(
                                    nombre = nombreTienda.trim(),
                                    correo = correo.trim(),
                                    contrasena = contrasena.trim(),
                                    rol = "tienda"
                                )
                                val usuario = authResponse.usuario
                                AuthManager.iniciarSesion(
                                    token = authResponse.token,
                                    usuario = UsuarioInfo(
                                        idUsuario = usuario.idUsuario,
                                        nombre = usuario.nombre,
                                        correo = usuario.correo,
                                        rol = usuario.rol
                                    )
                                )
                                val tiendaResponse = AuthApiService.createTienda(
                                    nombreTienda = nombreTienda.trim(),
                                    direccion = direccion.trim(),
                                    contacto = correo.trim(),
                                    idAdministrador = usuario.idUsuario
                                )
                                AuthManager.asignarTienda(
                                    TiendaInfo(
                                        tiendaResponse.tienda.idTienda,
                                        tiendaResponse.tienda.nombreTienda
                                    )
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
                else Text("Crear Tienda", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null,
    focusManager: androidx.compose.ui.focus.FocusManager
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { nextFocusRequester?.requestFocus() },
            onDone = { focusManager.clearFocus() }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .focusRequester(focusRequester),
        singleLine = true
    )
}
