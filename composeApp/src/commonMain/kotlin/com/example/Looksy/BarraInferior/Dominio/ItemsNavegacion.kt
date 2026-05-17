package com.example.Looksy.BarraInferior.Dominio

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import com.example.Looksy.BarraInferior.Datos.ModeloItemBarraInf

object ItemsNavegacion {
    val items = listOf(
        ModeloItemBarraInf(
            title = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "inicio"
        ),
        ModeloItemBarraInf(
            title = "Perfil",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "perfil"
        ),
        ModeloItemBarraInf(
            title = "Ventas",
            selectedIcon = Icons.Default.ShoppingCart,
            unselectedIcon = Icons.Default.ShoppingCart,
            route = "ventas"
        ),
        ModeloItemBarraInf(
            title = "Ajustes",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "ajustes"
        ),
        ModeloItemBarraInf(
            title = "Agregar",
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
            route = "agregar"
        )
    )
}
