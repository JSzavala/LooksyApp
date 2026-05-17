package com.example.Looksy.Mensajeria

object VentasRepository {

    val ventas = listOf(
        Venta(
            id = "ORD-001",
            producto = "Camisa Azul",
            cliente = "Juan Pérez",
            direccion = "Av. Insurgentes Sur 1234",
            fechaCompra = "10 May 2026",
            fechaEntrega = "15 May 2026",
            precio = "$299.00",
            cantidad = 2,
            estado = "Completada"
        ),
        Venta(
            id = "ORD-002",
            producto = "Pantalón Negro",
            cliente = "María García",
            direccion = "Reforma 222",
            fechaCompra = "11 May 2026",
            fechaEntrega = "16 May 2026",
            precio = "$450.00",
            cantidad = 1,
            estado = "Pendiente"
        )
    )

    fun obtenerVentaPorId(id: String): Venta? {
        return ventas.find { it.id == id }
    }
}