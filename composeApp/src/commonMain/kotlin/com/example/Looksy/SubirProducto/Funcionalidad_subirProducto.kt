package com.example.Looksy.SubirProducto
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Funcionalidad_subirProducto {
    var nombrePrenda by mutableStateOf("")
    var precioPrenda by mutableStateOf("")
    var tallasSeleccionadas = mutableStateListOf<String>()
    var descripcionPrenda by mutableStateOf("")
    var imagenPrenda by mutableStateOf("")

    fun publicar() {
        if (nombrePrenda.isNotEmpty() && precioPrenda.isNotEmpty()) {
            // Aquí conectarás con tu API de Node.js/Prisma
            println("Subiendo $nombrePrenda a la base de datos MySQL...")
        } else {
            println("Error: Faltan datos por llenar")
        }
    }


}