package com.example.Looksy

import com.example.Looksy.Login.Datos.ModeloLogin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ModeloLoginTest {

    @Test
    fun modeloLogin_debe_tener_valores_default() {
        val modelo = ModeloLogin()
        assertEquals("", modelo.usuario)
        assertEquals("", modelo.contrasena)
        assertNull(modelo.error)
        assertFalse(modelo.estaLogueado)
    }

    @Test
    fun modeloLogin_debe_aceptar_valores_personalizados() {
        val modelo = ModeloLogin(
            usuario = "test@test.com",
            contrasena = "pass123",
            error = "Error message",
            estaLogueado = true
        )
        assertEquals("test@test.com", modelo.usuario)
        assertEquals("pass123", modelo.contrasena)
        assertEquals("Error message", modelo.error)
        assertTrue(modelo.estaLogueado)
    }

    @Test
    fun modeloLogin_igualdad_estructurada() {
        val a = ModeloLogin("user", "pass", null, false)
        val b = ModeloLogin("user", "pass", null, false)
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
    }
}
