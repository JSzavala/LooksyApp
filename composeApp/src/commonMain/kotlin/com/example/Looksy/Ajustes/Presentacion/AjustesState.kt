package com.example.Looksy.Ajustes.Presentacion

data class AjustesState(
    val nombreTienda: String = "",
    val correoAdmin: String = "",
    val rfc: String = "",
    val isVerified: Boolean = false,
    val alertasServidorEnabled: Boolean = true,
    val modoOscuroEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val mensajeError: String? = null,
    val contrasena: String = "",
    val descripcion: String = "",
    val direccionDespacho: String = "No configurada"
)
