package com.example.looksy.datos.modelos

import androidx.compose.ui.graphics.vector.ImageVector

data class ModeloItemBarraInf(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
