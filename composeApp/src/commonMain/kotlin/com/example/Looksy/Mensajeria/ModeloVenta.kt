package com.example.Looksy.Mensajeria
data class Venta(
    val id: String,
    val producto: String,
    val cliente: String,
    val direccion: String,
    val fechaCompra: String,
    val fechaEntrega: String,
    val precio: String,
    val cantidad: Int,
    val estado: String // "Realizada" o "Pendiente"
)
