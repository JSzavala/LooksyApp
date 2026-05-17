package com.example.Looksy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.Looksy.Ajustes.Presentacion.ModeloAjustes
import com.example.Looksy.Ajustes.Presentacion.VistaAjustes
import com.example.Looksy.Ajustes.Presentacion.VistaEditarPerfil
import com.example.Looksy.Ajustes.Presentacion.VistaCambiarContrasena
import com.example.Looksy.Ajustes.Presentacion.VistaDireccionDespacho
import com.example.Looksy.BarraInferior.Presentacion.VistaBarraInferior
import com.example.Looksy.CrearCuenta.CuentaComprador
import com.example.Looksy.CrearCuenta.CuentaTienda
import com.example.Looksy.CrudTienda.Datos.ProductoRepository
import com.example.Looksy.CrudTienda.Presentacion.VistaCrudtienda
import com.example.Looksy.Login.Presentacion.VistaLogin
import com.example.Looksy.SeleccionTipoCuenta.Presentacion.VistaSeleccionTipoCuenta
import com.example.Looksy.SubirProducto.Funcionalidad_subirProducto
import com.example.Looksy.SubirProducto.VistaAgregarProducto
import com.example.Looksy.Mensajeria.VistaDetalleVenta
import com.example.Looksy.Mensajeria.VistaVentas

@Composable
fun App() {
    MaterialTheme {
        var estaLogueado by remember { mutableStateOf(false) }
        var mostrarSeleccionCuenta by remember { mutableStateOf(false) }
        var pantallaRegistro by remember { mutableStateOf<String?>(null) }

        when {
            pantallaRegistro == "tienda" -> {
                CuentaTienda(
                    onVolver = { pantallaRegistro = null },
                    onIrLogin = {
                        pantallaRegistro = null
                        mostrarSeleccionCuenta = false
                    }
                )
            }

            pantallaRegistro == "comprador" -> {
                CuentaComprador(
                    onVolver = { pantallaRegistro = null },
                    onIrLogin = {
                        pantallaRegistro = null
                        mostrarSeleccionCuenta = false
                    }
                )
            }

            mostrarSeleccionCuenta -> {
                VistaSeleccionTipoCuenta(
                    onSeleccionar = { tipo -> pantallaRegistro = tipo },
                    onVolver = { mostrarSeleccionCuenta = false }
                )
            }

            !estaLogueado -> {
                VistaLogin(
                    onLoginSuccess = { estaLogueado = true },
                    onCreateAccount = { mostrarSeleccionCuenta = true }
                )
            }

            else -> {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val navController = rememberNavController()
    // Inicialización del ViewModel compartido para que los datos persistan en la sesión
    val viewModelAjustes = remember { ModeloAjustes() }

    Scaffold(
        bottomBar = {
            VistaBarraInferior(
                navController = navController,
                esTienda = true
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = "perfil"
            ) {
                composable("perfil") {
                    VistaCrudtienda(navController, viewModelAjustes)
                }
                composable(
                    route = "agregar?productoId={productoId}",
                    arguments = listOf(
                        navArgument("productoId") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) { backStackEntry ->
                    val productoIdStr = backStackEntry.arguments?.getString("productoId")
                    val productoId = productoIdStr?.toIntOrNull()
                    val viewModel = remember { Funcionalidad_subirProducto() }
                    
                    LaunchedEffect(productoId) {
                        if (productoId != null) {
                            ProductoRepository.obtenerProductoPorId(productoId)?.let {
                                viewModel.cargarDatosParaEditar(it)
                            }
                        }
                    }
                    
                    VistaAgregarProducto(
                        viewModel = viewModel,
                        navController = navController,
                        onVolver = { navController.popBackStack() }
                    )
                }
                composable("ventas") {
                    VistaVentas(navController)
                }

                composable("detalleVenta/{ventaId}") { backStackEntry ->
                    val ventaId = backStackEntry.arguments
                        ?.getString("ventaId") ?: ""
                    VistaDetalleVenta(
                        ventaId = ventaId,
                        onVolver = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("ajustes") {
                    VistaAjustes(
                        viewModel = viewModelAjustes,
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToEditarPerfil = { navController.navigate("editarPerfil") },
                        onNavigateToCambiarContrasena = { navController.navigate("cambiarContrasena") },
                        onNavigateToDireccionDespacho = { navController.navigate("direccionDespacho") }
                    )
                }
                composable("editarPerfil") {
                    VistaEditarPerfil(
                        viewModel = viewModelAjustes,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable("cambiarContrasena") {
                    VistaCambiarContrasena(
                        viewModel = viewModelAjustes,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable("direccionDespacho") {
                    VistaDireccionDespacho(
                        viewModel = viewModelAjustes,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
