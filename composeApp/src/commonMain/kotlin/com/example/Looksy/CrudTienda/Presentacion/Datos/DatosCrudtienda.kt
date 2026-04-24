package com.example.Looksy.CrudTienda.Presentacion.Datos


import androidx.compose.ui.graphics.Color

data class ProductoTienda(
    val id: Int,
    val nombre: String,
    val precio: String,
    var tallasSeleccionadas: List<String>,
    var descricpion: String,
    var stock: Int,
    val colorPlaceholder: Color
)