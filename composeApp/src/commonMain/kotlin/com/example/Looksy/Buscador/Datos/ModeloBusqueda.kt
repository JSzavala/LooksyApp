package com.example.Looksy.Buscador.Datos

data class ModeloBusqueda(
    val query: String = "",
    val placeholder: String = "Buscar...",
    val isActive: Boolean = false
)
