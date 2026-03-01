package com.example.Looksy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.Looksy.BarraInferior.Presentacion.VistaBarraInferior
import com.example.Looksy.ListadoImagenes.Presentacion.VistaListadoImagenes
import com.example.Looksy.Perfil.Presentacion.VistaPerfil
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
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
                        VistaPerfil()
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
}
