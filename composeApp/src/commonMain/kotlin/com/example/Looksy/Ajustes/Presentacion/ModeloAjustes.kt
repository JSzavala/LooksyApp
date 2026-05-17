package com.example.Looksy.Ajustes.Presentacion
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ModeloAjustes {
    // El estado expuesto hacia la vista
    var state by mutableStateOf(AjustesState(
        nombreTienda = "",
        correoAdmin = "",
        descripcion = "",
        rfc = "",
        isVerified = false,
        alertasServidorEnabled = true,
        modoOscuroEnabled = false,
        isLoading = false,
        mensajeError = null,
        contrasena = "",
        direccionDespacho = "No configurada"
    ))
        private set

    init {
        cargarDatosDeLaTienda()
    }

    private fun cargarDatosDeLaTienda() {
        // SIMULACIÓN: Aquí harías un fetch/get a tu backend Node.js
        state = state.copy(isLoading = true)

        // Supongamos que tu API responde exitosamente con estos datos:
        state = state.copy(
            nombreTienda = "Looksy Boutique",
            correoAdmin = "owner@looksy.com",
            rfc = "XAXX010101000",
            isVerified = false, // Cambiar a true cuando esté validado en MySQL
            isLoading = false,
            direccionDespacho = "Av. Siempre Viva 742"
        )
    }

    // Acción para actualizar el switch de alertas
    fun toggleAlertasServidor(enabled: Boolean) {
        state = state.copy(alertasServidorEnabled = enabled)
    }

    // Acción para actualizar el switch de modo oscuro
    fun toggleModoOscuro(enabled: Boolean) {
        state = state.copy(modoOscuroEnabled = enabled)
    }

    // Acción para cuando el dueño envía su RFC a validar
    fun registrarRfcYVerificar(nuevoRfc: String) {
        state = state.copy(rfc = nuevoRfc, isVerified = true)
    }

    fun actualizarNombreTienda(nuevoNombre: String) {
        state = state.copy(nombreTienda = nuevoNombre)
    }

    fun actualizarCorreoAdmin(correo: String) {
        state = state.copy(correoAdmin = correo)
    }

    fun actualizarContraseña(contrasena: String) {
        state = state.copy(contrasena = contrasena)
    }

    fun actualizarDescripcion(descripcion: String) {
        state = state.copy(descripcion = descripcion)
    }

    fun actualizarDireccionDespacho(nuevaDireccion: String) {
        state = state.copy(direccionDespacho = nuevaDireccion)
    }
}
