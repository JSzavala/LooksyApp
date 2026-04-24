package com.example.Looksy.CrudTienda.Datos

import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProductoRepository {
    private val _productos = MutableStateFlow<List<ProductoTienda>>(emptyList())
    val productos: StateFlow<List<ProductoTienda>> = _productos.asStateFlow()

    fun agregarProducto(producto: ProductoTienda) {
        _productos.value = _productos.value + producto
    }

    fun eliminarProducto(id: Int) {
        _productos.value = _productos.value.filter { it.id != id }
    }

    fun actualizarProducto(productoEditado: ProductoTienda) {
        _productos.value = _productos.value.map {
            if (it.id == productoEditado.id) productoEditado else it
        }
    }
    fun obtenerProductoPorId(id: Int): ProductoTienda? {
        return _productos.value.find { it.id == id }
    }
}