package com.example.looksy.datos.modelos

data class ModeloLogin(
    val usuario: String = "",
    val contrasena: String = "",
    val error: String? = null,
    val estaLogueado: Boolean = false
)
