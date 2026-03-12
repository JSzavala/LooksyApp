package com.example.looksy.datos.modelos

data class ModeloBusqueda(
    val query: String = "",
    val placeholder: String = "Buscar...",
    val isActive: Boolean = false
)
