package com.example.Looksy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.Looksy.BarraInferior.Presentacion.VistaBarraInferior
import com.example.Looksy.CrudTienda.Presentacion.VistaCrudtienda
import com.example.Looksy.ListadoImagenes.Presentacion.VistaListadoImagenes
import com.example.Looksy.Login.Presentacion.VistaLogin
import com.example.Looksy.Perfil.Presentacion.VistaPerfil
import com.example.Looksy.SubirProducto.VistaAgregarProducto
import com.example.Looksy.VistaProducto.Presentacion.VistaListadoProductos

@Composable
fun App() {
    MaterialTheme {
        //Para saltar el login durante las pruebas
        val saltarLoginParaPruebas = false
        var estaLogueado by remember { mutableStateOf(saltarLoginParaPruebas) }

        if (!estaLogueado) {
            VistaLogin(onLoginSuccess = { estaLogueado = true })
        } else {
            MainContent()
        }
//        MainContent()
    }
}

@Composable
fun MainContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            VistaBarraInferior(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = "inicio"
            ) {
                composable("inicio") {
                    VistaListadoImagenes()
                }
                composable("buscar") {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Pantalla de Búsqueda")
                    }
                }
                composable("perfil") {
                    VistaCrudtienda(navController)
                }
                composable("agregar"){
                    VistaAgregarProducto(
                        navController = navController,
                        onVolver = { navController.popBackStack() }
                    )
                }
                composable("ajustes") {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Pantalla de Ajustes")
                    }
                }
            }
        }
    }
}
