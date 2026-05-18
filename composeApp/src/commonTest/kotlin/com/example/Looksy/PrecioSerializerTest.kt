package com.example.Looksy

import com.example.Looksy.CrudTienda.Datos.PrecioSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PrecioSerializerTest {

    @Serializable
    data class TestProduct(
        @Serializable(with = PrecioSerializer::class)
        val precio: Double
    )

    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    @Test
    fun precioSerializer_debe_deserializar_numero_entero() {
        val result = json.decodeFromString<TestProduct>("""{"precio":42}""")
        assertEquals(42.0, result.precio, 0.001)
    }

    @Test
    fun precioSerializer_debe_deserializar_numero_decimal() {
        val result = json.decodeFromString<TestProduct>("""{"precio":19.99}""")
        assertEquals(19.99, result.precio, 0.001)
    }

    @Test
    fun precioSerializer_debe_deserializar_string_numerico() {
        val result = json.decodeFromString<TestProduct>("""{"precio":"25.50"}""")
        assertEquals(25.50, result.precio, 0.001)
    }

    @Test
    fun precioSerializer_debe_deserializar_cero() {
        val result = json.decodeFromString<TestProduct>("""{"precio":0}""")
        assertEquals(0.0, result.precio, 0.001)
    }
}
