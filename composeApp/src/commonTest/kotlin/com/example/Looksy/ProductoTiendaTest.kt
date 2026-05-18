package com.example.Looksy

import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ProductoTiendaTest {

    @Test
    fun precioFormateado_debe_formatear_precio_entero() {
        val producto = ProductoTienda(1, "Camisa", null, 25.0, 10, true, 1)
        assertEquals("$25.00", producto.precioFormateado)
    }

    @Test
    fun precioFormateado_debe_formatear_precio_con_decimales() {
        val producto = ProductoTienda(1, "Pantalon", null, 19.99, 10, true, 1)
        assertEquals("$19.99", producto.precioFormateado)
    }

    @Test
    fun imagenColor_debe_ser_determinista_por_id() {
        val p1 = ProductoTienda(1, "A", null, 10.0, 1, true, 1)
        val p2 = ProductoTienda(1, "B", null, 10.0, 1, true, 1)
        val p3 = ProductoTienda(2, "C", null, 10.0, 1, true, 1)

        assertEquals(p1.imagenColor, p2.imagenColor)
        assertNotEquals(p1.imagenColor, p3.imagenColor)
    }

    @Test
    fun precioFormateado_debe_manejar_precios_grandes() {
        val producto = ProductoTienda(1, "TV", null, 4999.99, 10, true, 1)
        assertEquals("$4999.99", producto.precioFormateado)
    }

    @Test
    fun precioFormateado_debe_manejar_precios_con_un_decimal() {
        val producto = ProductoTienda(1, "Test", null, 10.5, 10, true, 1)
        assertEquals("$10.50", producto.precioFormateado)
    }

    @Test
    fun descripcion_debe_aceptar_nulo() {
        val producto = ProductoTienda(1, "Test", null, 10.0, 5, true, 1)
        assertNull(producto.descripcion)
    }

    @Test
    fun descripcion_debe_aceptar_texto() {
        val producto = ProductoTienda(1, "Test", "Una descripcion", 10.0, 5, true, 1)
        assertNotNull(producto.descripcion)
        assertEquals("Una descripcion", producto.descripcion)
    }

    @Test
    fun imagenColor_debe_ser_valido_return_long_color() {
        val colores = listOf(1, 2, 3, 4, 5, 6).map { id ->
            ProductoTienda(id, "P$id", null, 10.0, 1, true, 1).imagenColor
        }
        assertEquals(6, colores.distinct().size)
    }
}
