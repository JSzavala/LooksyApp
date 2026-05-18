package com.example.Looksy

import com.example.Looksy.CrudTienda.Datos.ProductoRepository
import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductoRepositoryTest {

    @AfterTest
    fun cleanup() {
        ProductoRepository.reiniciar()
    }

    @Test
    fun productos_debe_iniciar_vacio() {
        assertTrue(ProductoRepository.productos.value.isEmpty())
    }

    @Test
    fun reiniciar_debe_limpiar_productos() {
        ProductoRepository.reiniciar()
        assertTrue(ProductoRepository.productos.value.isEmpty())
    }

    @Test
    fun productos_debe_ser_StateFlow_accesible() {
        val productos = ProductoRepository.productos.value
        assertEquals(0, productos.size)
    }
}
