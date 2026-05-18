package com.example.Looksy

import com.example.Looksy.Ajustes.Presentacion.AjustesState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AjustesStateTest {

    @Test
    fun ajustesState_debe_tener_valores_default() {
        val state = AjustesState()
        assertEquals("", state.nombreTienda)
        assertEquals("", state.correoAdmin)
        assertEquals("", state.rfc)
        assertFalse(state.isVerified)
        assertTrue(state.alertasServidorEnabled)
        assertFalse(state.modoOscuroEnabled)
        assertFalse(state.isLoading)
        assertEquals("No configurada", state.direccionDespacho)
    }

    @Test
    fun ajustesState_debe_aceptar_valores_personalizados() {
        val state = AjustesState(
            nombreTienda = "Tienda Test",
            correoAdmin = "admin@test.com",
            rfc = "RFC123456",
            isVerified = true,
            alertasServidorEnabled = false,
            modoOscuroEnabled = true,
            isLoading = true,
            mensajeError = "Error",
            contrasena = "secret",
            descripcion = "Desc",
            direccionDespacho = "Calle 123"
        )
        assertEquals("Tienda Test", state.nombreTienda)
        assertEquals("admin@test.com", state.correoAdmin)
        assertTrue(state.isVerified)
        assertFalse(state.alertasServidorEnabled)
        assertTrue(state.modoOscuroEnabled)
        assertTrue(state.isLoading)
        assertEquals("Calle 123", state.direccionDespacho)
    }

    @Test
    fun ajustesState_debe_tener_mensajeError_null_por_default() {
        val state = AjustesState()
        assertEquals(null, state.mensajeError)
    }
}
