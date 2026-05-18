package com.example.Looksy.CrudTienda.Datos

data class UsuarioInfo(
    val idUsuario: Int,
    val nombre: String,
    val correo: String,
    val rol: String
)

data class TiendaInfo(
    val idTienda: Int,
    val nombreTienda: String
)

object AuthManager {
    var token: String? = null
        private set
    var currentUser: UsuarioInfo? = null
        private set
    var currentStore: TiendaInfo? = null
        private set

    val isLoggedIn: Boolean get() = token != null
    val idTienda: Int? get() = currentStore?.idTienda

    fun iniciarSesion(token: String, usuario: UsuarioInfo) {
        this.token = token
        this.currentUser = usuario
    }

    fun asignarTienda(tienda: TiendaInfo) {
        this.currentStore = tienda
    }

    fun cerrarSesion() {
        token = null
        currentUser = null
        currentStore = null
    }

    val authHeader: String?
        get() = token?.let { "Bearer $it" }
}
