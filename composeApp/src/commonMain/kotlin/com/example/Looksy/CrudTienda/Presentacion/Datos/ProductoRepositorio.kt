package com.example.Looksy.CrudTienda.Datos

import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProductoRepository {
    private val _productos = MutableStateFlow<List<ProductoTienda>>(emptyList())
    val productos: StateFlow<List<ProductoTienda>> = _productos.asStateFlow()

    private var cargados = false
    private var cargando = false

    suspend fun cargarProductos(idTienda: Int? = null) {
        if (cargados || cargando) return
        realizarCarga(idTienda = idTienda, intentos = 3)
    }

    suspend fun forzarCarga(idTienda: Int? = null) {
        cargados = false
        realizarCarga(idTienda = idTienda, intentos = 3)
    }

    private suspend fun realizarCarga(idTienda: Int? = null, intentos: Int = 1) {
        if (cargando) return
        cargando = true
        val tiendaId = idTienda ?: AuthManager.idTienda
        repeat(intentos) { intento ->
            try {
                if (tiendaId != null) {
                    val response = ProductoApiService.getProductosByTienda(tiendaId)
                    _productos.value = response.productos.map { it.aDominio(nombreTiendaOverride = response.tienda.nombreTienda, idTiendaOverride = tiendaId) }
                } else {
                    val response = ProductoApiService.getProductos()
                    _productos.value = response.productos.map { it.aDominio() }
                }
                cargados = true
                cargando = false
                println("Productos cargados exitosamente: ${_productos.value.size}")
                return
            } catch (e: Exception) {
                println("Error al cargar productos (intento ${intento + 1}/$intentos): ${e.message}")
                if (intento < intentos - 1) {
                    delay((1000L * (intento + 1)))
                }
            }
        }
        cargando = false
    }

    suspend fun cargarProductosPorTienda(idTienda: Int) {
        try {
            val response = ProductoApiService.getProductosByTienda(idTienda)
            _productos.value = response.productos.map { it.aDominio(nombreTiendaOverride = response.tienda.nombreTienda, idTiendaOverride = idTienda) }
            cargados = true
        } catch (e: Exception) {
            println("Error al cargar productos de tienda: ${e.message}")
        }
    }

    suspend fun agregarProducto(producto: ProductoTienda) {
        val idTienda = AuthManager.idTienda ?: producto.idTienda
        try {
            val request = CreateProductoRequest(
                nombre = producto.nombre,
                descripcion = producto.descripcion,
                precio = producto.precio,
                stock = producto.stock,
                disponible = producto.disponible,
                idTienda = idTienda
            )
            val response = ProductoApiService.createProducto(request)
            val creado = response.producto.aDominio()
            _productos.value = _productos.value + creado
        } catch (e: Exception) {
            println("Error al crear producto: ${e.message}")
        }
    }

    suspend fun actualizarProducto(productoEditado: ProductoTienda) {
        try {
            val request = UpdateProductoRequest(
                nombre = productoEditado.nombre,
                descripcion = productoEditado.descripcion,
                precio = productoEditado.precio,
                stock = productoEditado.stock,
                disponible = productoEditado.disponible
            )
            val response = ProductoApiService.updateProducto(productoEditado.idProducto, request)
            val actualizado = response.producto.aDominio()
            _productos.value = _productos.value.map {
                if (it.idProducto == actualizado.idProducto) actualizado else it
            }
        } catch (e: Exception) {
            println("Error al actualizar producto: ${e.message}")
        }
    }

    suspend fun eliminarProducto(idProducto: Int) {
        try {
            ProductoApiService.deleteProducto(idProducto)
            _productos.value = _productos.value.filter { it.idProducto != idProducto }
        } catch (e: Exception) {
            println("Error al eliminar producto: ${e.message}")
        }
    }

    suspend fun obtenerProductoPorId(idProducto: Int): ProductoTienda? {
        if (!cargados) cargarProductos()
        return _productos.value.find { it.idProducto == idProducto }
    }

    fun reiniciar() {
        _productos.value = emptyList()
        cargados = false
        cargando = false
    }

    private fun ProductoTiendaDto.aDominio(nombreTiendaOverride: String? = null, idTiendaOverride: Int? = null): ProductoTienda {
        return ProductoTienda(
            idProducto = idProducto,
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            stock = stock,
            disponible = disponible,
            idTienda = idTienda ?: idTiendaOverride ?: 0,
            nombreTienda = nombreTiendaOverride ?: tienda?.nombreTienda
        )
    }
}
