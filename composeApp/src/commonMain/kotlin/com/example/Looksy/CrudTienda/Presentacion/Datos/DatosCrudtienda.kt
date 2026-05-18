package com.example.Looksy.CrudTienda.Presentacion.Datos

import kotlin.math.roundToInt

data class ProductoTienda(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    val disponible: Boolean,
    val idTienda: Int,
    val nombreTienda: String? = null
) {
    val precioFormateado: String
        get() {
            val entero = precio.toInt()
            val decimales = ((precio - entero) * 100).roundToInt()
            return "$$entero.${decimales.toString().padStart(2, '0')}"
        }

    val imagenColor: Long
        get() = COLORES[idProducto % COLORES.size]

    companion object {
        private val COLORES = listOf(
            0xFFA0522D, 0xFF4A90D9, 0xFF50B86C,
            0xFFE74C3C, 0xFFF39C12, 0xFF9B59B6
        )
    }
}
