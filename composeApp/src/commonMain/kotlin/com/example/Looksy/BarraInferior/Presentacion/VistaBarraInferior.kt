package com.example.Looksy.BarraInferior.Presentacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.Looksy.BarraInferior.Dominio.ItemsNavegacion

@Composable
fun VistaBarraInferior(
    navController: NavController,
    esTienda: Boolean
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        ItemsNavegacion.items.forEach { item ->

            // Ocultar inicio si es tienda
            if (esTienda && item.route == "inicio") {
                return@forEach
            }

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.selectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }
}
