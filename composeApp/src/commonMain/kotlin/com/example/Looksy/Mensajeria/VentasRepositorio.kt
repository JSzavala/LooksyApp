package com.example.Looksy.Mensajeria

object VentasRepository {

    val ventas = listOf(
        Venta(
            id = "1",
            producto = "Camisa Azul",
            cliente = "Andre",
            direccion = "Av. Insurgentes Sur 1234",
            fechaCompra = "10 May 2026",
            fechaEntrega = "15 May 2026",
            precio = "$299.00",
            cantidad = 2,
            estado = "Completada"
        ),
        Venta(
            id = "2",
            producto = "Pantalón Negro",
            cliente = "Alejandro Nava",
            direccion = "Reforma 222",
            fechaCompra = "12 May 2026",
            fechaEntrega = "21 May 2026",
            precio = "$450.00",
            cantidad = 1,
            estado = "Pendiente"
        ),
        Venta(
            id = "3",
            producto = "Playera",
            cliente = "Jonathan Alcantar",
            direccion = "Reforma 222",
            fechaCompra = "14 May 2026",
            fechaEntrega = "16 May 2026",
            precio = "$350.00",
            cantidad = 1,
            estado = "Completada"
        )
    )

    fun obtenerVentaPorId(id: String): Venta? {
        return ventas.find { it.id == id }
    }
}