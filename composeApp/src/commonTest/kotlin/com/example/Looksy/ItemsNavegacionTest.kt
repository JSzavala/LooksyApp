package com.example.Looksy

import com.example.Looksy.BarraInferior.Dominio.ItemsNavegacion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ItemsNavegacionTest {

    @Test
    fun items_debe_contener_5_elementos() {
        assertEquals(5, ItemsNavegacion.items.size)
    }

    @Test
    fun items_deben_tener_rutas_definidas() {
        ItemsNavegacion.items.forEach { item ->
            assertNotNull(item.title)
            assertNotNull(item.route)
            assertTrue(item.route.isNotEmpty())
        }
    }
}
