package com.example.Looksy.SubirProducto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.Looksy.CrudTienda.Datos.ProductoRepository
import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Funcionalidad_subirProducto {
    var idEdicion by mutableStateOf<Int?>(null)
    var nombrePrenda by mutableStateOf("")
    var precioPrenda by mutableStateOf("")
    var descripcionPrenda by mutableStateOf("")
    var stockPrenda by mutableStateOf("")
    var imagenPrenda by mutableStateOf("")

    fun cargarDatosParaEditar(producto: ProductoTienda) {
        idEdicion = producto.idProducto
        nombrePrenda = producto.nombre
        precioPrenda = if (producto.precio == producto.precio.toLong().toDouble()) {
            producto.precio.toLong().toString()
        } else {
            producto.precio.toString()
        }
        descripcionPrenda = producto.descripcion ?: ""
        stockPrenda = producto.stock.toString()
    }

    fun publicar(navController: NavHostController, scope: CoroutineScope) {
        if (nombrePrenda.isNotEmpty() && precioPrenda.isNotEmpty()) {
            val precio = precioPrenda.toDoubleOrNull() ?: 0.0
            val stock = stockPrenda.toIntOrNull() ?: 0

            val nuevo = ProductoTienda(
                idProducto = idEdicion ?: 0,
                nombre = nombrePrenda,
                descripcion = descripcionPrenda.ifBlank { null },
                precio = precio,
                stock = stock,
                disponible = true,
                idTienda = 1
            )

            scope.launch {
                if (idEdicion == null) {
                    ProductoRepository.agregarProducto(nuevo)
                } else {
                    ProductoRepository.actualizarProducto(nuevo)
                }
                navController.popBackStack()
                navController.navigate("perfil")
            }
        }
    }
}
