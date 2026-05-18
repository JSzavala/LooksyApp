package com.example.Looksy.Login.Presentacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.Looksy.CrudTienda.Datos.ApiException
import com.example.Looksy.CrudTienda.Datos.AuthApiService
import com.example.Looksy.CrudTienda.Datos.AuthManager
import com.example.Looksy.CrudTienda.Datos.ProductoRepository
import com.example.Looksy.CrudTienda.Datos.TiendaInfo
import com.example.Looksy.CrudTienda.Datos.UsuarioInfo
import kotlinx.coroutines.launch

@Composable
fun VistaLogin(
    onLoginSuccess: () -> Unit,
    onCreateAccount: () -> Unit
) {
    val usuarioFocus = remember { FocusRequester() }
    val contrasenaFocus = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }
    var mostrarContrasena by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
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
            value = correo,
            onValueChange = { correo = it; error = null },
            label = { Text("Correo") },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { contrasenaFocus.requestFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .focusRequester(usuarioFocus),
            singleLine = true
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it; error = null },
            label = { Text("Contraseña") },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (mostrarContrasena) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            trailingIcon = {
                val icon = if (mostrarContrasena) Icons.Default.Visibility
                else Icons.Default.VisibilityOff
                IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                    Icon(imageVector = icon, contentDescription = "Mostrar contraseña")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .focusRequester(contrasenaFocus),
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
                if (correo.isBlank() || contrasena.isBlank()) {
                    error = "Llena todos los campos"
                    return@Button
                }
                if (contrasena.length < 5) {
                    error = "La contraseña debe tener al menos 5 caracteres"
                    return@Button
                }
                if (!correo.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
                    error = "El formato del correo no es válido"
                    return@Button
                }

                cargando = true
                error = null
                scope.launch {
                    try {
                        val response = AuthApiService.login(correo.trim(), contrasena.trim())
                        val usuario = response.usuario
                        AuthManager.iniciarSesion(
                            token = response.token,
                            usuario = UsuarioInfo(
                                idUsuario = usuario.idUsuario,
                                nombre = usuario.nombre,
                                correo = usuario.correo,
                                rol = usuario.rol
                            )
                        )
                        var idTiendaProductos: Int? = null
                        if (usuario.rol == "tienda") {
                            try {
                                val tiendasResponse = AuthApiService.getTiendasByAdmin(usuario.idUsuario)
                                val miTienda = tiendasResponse.tiendas.find {
                                    it.idAdministrador == usuario.idUsuario
                                }
                                if (miTienda != null) {
                                    AuthManager.asignarTienda(
                                        TiendaInfo(miTienda.idTienda, miTienda.nombreTienda)
                                    )
                                    idTiendaProductos = miTienda.idTienda
                                }
                            } catch (e: Exception) {
                                println("Error al obtener tienda: ${e.message}")
                            }
                        }
                        ProductoRepository.cargarProductos(idTiendaProductos)
                        onLoginSuccess()
                    } catch (e: ApiException) {
                        println("Login ApiException (${e.statusCode}): ${e.message}")
                        error = e.message ?: "Error de autenticación"
                    } catch (e: Exception) {
                        println("Login Exception: ${e::class.simpleName}: ${e.message}")
                        error = "Error de conexión: ${e.message}"
                    } finally {
                        cargando = false
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
            else Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Si aún no tienes cuenta puedes registrate aquí ",
                modifier = Modifier.clickable { onCreateAccount() },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
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
