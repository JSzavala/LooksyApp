package com.example.Looksy

import com.example.Looksy.CrudTienda.Datos.AuthManager
import com.example.Looksy.CrudTienda.Datos.TiendaInfo
import com.example.Looksy.CrudTienda.Datos.UsuarioInfo
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AuthManagerTest {

    @AfterTest
    fun cleanup() {
        AuthManager.cerrarSesion()
    }

    @Test
    fun iniciarSesion_debe_establecer_token_y_usuario() {
        val usuario = UsuarioInfo(idUsuario = 1, nombre = "Admin", correo = "admin@test.com", rol = "tienda")
        AuthManager.iniciarSesion("token123", usuario)

        assertTrue(AuthManager.isLoggedIn)
        assertEquals("token123", AuthManager.token)
        assertEquals("Admin", AuthManager.currentUser?.nombre)
        assertEquals("Bearer token123", AuthManager.authHeader)
    }

    @Test
    fun asignarTienda_debe_establecer_tienda_actual() {
        AuthManager.iniciarSesion("t", UsuarioInfo(1, "A", "a@a.com", "tienda"))
        AuthManager.asignarTienda(TiendaInfo(idTienda = 5, nombreTienda = "Mi Tienda"))

        assertEquals(5, AuthManager.idTienda)
        assertEquals("Mi Tienda", AuthManager.currentStore?.nombreTienda)
    }

    @Test
    fun cerrarSesion_debe_limpiar_todos_los_datos() {
        AuthManager.iniciarSesion("token", UsuarioInfo(1, "Test", "t@t.com", "tienda"))
        AuthManager.asignarTienda(TiendaInfo(1, "Tienda"))

        AuthManager.cerrarSesion()

        assertFalse(AuthManager.isLoggedIn)
        assertNull(AuthManager.token)
        assertNull(AuthManager.currentUser)
        assertNull(AuthManager.currentStore)
        assertNull(AuthManager.idTienda)
    }

    @Test
    fun authHeader_debe_ser_null_cuando_no_hay_sesion() {
        AuthManager.cerrarSesion()

        assertNull(AuthManager.authHeader)
    }

    @Test
    fun idTienda_debe_ser_null_sin_tienda_asignada() {
        AuthManager.iniciarSesion("t", UsuarioInfo(1, "A", "a@a.com", "tienda"))

        assertNull(AuthManager.idTienda)
    }

    @Test
    fun isLoggedIn_debe_ser_false_sin_token() {
        AuthManager.cerrarSesion()

        assertFalse(AuthManager.isLoggedIn)
    }
}
