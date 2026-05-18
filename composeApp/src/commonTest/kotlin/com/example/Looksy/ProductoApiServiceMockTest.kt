package com.example.Looksy

import com.example.Looksy.CrudTienda.Datos.ApiErrorBody
import com.example.Looksy.CrudTienda.Datos.CreateProductoRequest
import com.example.Looksy.CrudTienda.Datos.DeleteProductoResponse
import com.example.Looksy.CrudTienda.Datos.ProductoListResponse
import com.example.Looksy.CrudTienda.Datos.ProductoResponse
import com.example.Looksy.CrudTienda.Datos.ProductosByTiendaResponse
import com.example.Looksy.CrudTienda.Datos.UpdateProductoRequest
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductoApiServiceMockTest {

    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    private fun createMockClient(
        status: HttpStatusCode = HttpStatusCode.OK,
        responseBody: String = ""
    ): HttpClient {
        val mockEngine = MockEngine { request ->
            respond(
                content = responseBody,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(this@ProductoApiServiceMockTest.json)
            }
        }
    }

    @Test
    fun getProductos_debe_deserializar_lista() = runTestWithClient(
        createMockClient(responseBody = """
            {
                "productos": [
                    {"idProducto": 1, "nombre": "Camisa", "precio": 25.99, "stock": 10, "disponible": true, "idTienda": 1},
                    {"idProducto": 2, "nombre": "Pantalon", "precio": 35.50, "stock": 5, "disponible": true, "idTienda": 1}
                ],
                "total": 2
            }
        """.trimIndent())
    ) { client ->
        val response = client.get("http://test/api/productos")
        val listResponse = json.decodeFromString<ProductoListResponse>(response.bodyAsText())

        assertEquals(2, listResponse.total)
        assertEquals("Camisa", listResponse.productos[0].nombre)
        assertEquals(25.99, listResponse.productos[0].precio, 0.001)
        assertEquals(5, listResponse.productos[1].stock)
    }

    @Test
    fun getProductosByTienda_debe_filtrar_por_tienda() = runTestWithClient(
        createMockClient(responseBody = """
            {
                "tienda": {"nombreTienda": "Tienda Test"},
                "productos": [
                    {"idProducto": 1, "nombre": "Producto 1", "precio": 10.0, "stock": 3, "disponible": true, "idTienda": 1}
                ],
                "total": 1
            }
        """.trimIndent())
    ) { client ->
        val response = client.get("http://test/api/tiendas/1/productos")
        val byTiendaResponse = json.decodeFromString<ProductosByTiendaResponse>(response.bodyAsText())

        assertEquals("Tienda Test", byTiendaResponse.tienda.nombreTienda)
        assertEquals(1, byTiendaResponse.total)
        assertEquals("Producto 1", byTiendaResponse.productos[0].nombre)
    }

    @Test
    fun createProducto_debe_serializar_y_deserializar() = runTestWithClient(
        createMockClient(responseBody = """
            {
                "producto": {"idProducto": 3, "nombre": "Nuevo Producto", "descripcion": "Descripcion", "precio": 15.00, "stock": 20, "disponible": true, "idTienda": 1}
            }
        """.trimIndent())
    ) { client ->
        val request = CreateProductoRequest(
            nombre = "Nuevo Producto",
            descripcion = "Descripcion",
            precio = 15.00,
            stock = 20,
            disponible = true,
            idTienda = 1
        )

        val response = client.post("http://test/api/productos") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        val productoResponse = json.decodeFromString<ProductoResponse>(response.bodyAsText())

        assertEquals(3, productoResponse.producto.idProducto)
        assertEquals("Nuevo Producto", productoResponse.producto.nombre)
        assertEquals(15.00, productoResponse.producto.precio, 0.001)
    }

    @Test
    fun updateProducto_debe_actualizar_producto() = runTestWithClient(
        createMockClient(responseBody = """
            {
                "producto": {"idProducto": 1, "nombre": "Actualizado", "precio": 30.00, "stock": 8, "disponible": true, "idTienda": 1}
            }
        """.trimIndent())
    ) { client ->
        val request = UpdateProductoRequest(
            nombre = "Actualizado",
            precio = 30.00,
            stock = 8
        )

        val response = client.put("http://test/api/productos/1") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        val productoResponse = json.decodeFromString<ProductoResponse>(response.bodyAsText())

        assertEquals("Actualizado", productoResponse.producto.nombre)
        assertEquals(30.00, productoResponse.producto.precio, 0.001)
    }

    @Test
    fun deleteProducto_debe_eliminar_producto() = runTestWithClient(
        createMockClient(responseBody = """
            {"mensaje": "Producto eliminado exitosamente", "idProducto": 1}
        """.trimIndent())
    ) { client ->
        val response = client.delete("http://test/api/productos/1")
        val deleteResponse = json.decodeFromString<DeleteProductoResponse>(response.bodyAsText())

        assertEquals("Producto eliminado exitosamente", deleteResponse.mensaje)
        assertEquals(1, deleteResponse.idProducto)
    }

    @Test
    fun createProducto_debe_manejar_error_de_validacion() = runTestWithClient(
        createMockClient(
            status = HttpStatusCode.BadRequest,
            responseBody = """{"error":"Bad Request","mensaje":"El nombre es requerido"}"""
        )
    ) { client ->
        val response = client.post("http://test/api/productos") {
            contentType(ContentType.Application.Json)
            setBody(CreateProductoRequest("", null, 0.0, 0, true, 1))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        val error = json.decodeFromString<ApiErrorBody>(response.bodyAsText())
        assertEquals("El nombre es requerido", error.mensaje)
    }

    @Test
    fun deleteProducto_debe_manejar_error_404() = runTestWithClient(
        createMockClient(
            status = HttpStatusCode.NotFound,
            responseBody = """{"error":"Not Found","mensaje":"Producto no encontrado"}"""
        )
    ) { client ->
        val response = client.delete("http://test/api/productos/999")
        val error = json.decodeFromString<ApiErrorBody>(response.bodyAsText())

        assertEquals("Producto no encontrado", error.mensaje)
    }

    @Test
    fun getProductos_debe_manejar_precio_como_string() = runTestWithClient(
        createMockClient(responseBody = """
            {
                "productos": [
                    {"idProducto": 1, "nombre": "Test", "precio": "19.99", "stock": 10, "disponible": true, "idTienda": 1}
                ],
                "total": 1
            }
        """.trimIndent())
    ) { client ->
        val response = client.get("http://test/api/productos")
        val listResponse = json.decodeFromString<ProductoListResponse>(response.bodyAsText())

        assertEquals(19.99, listResponse.productos[0].precio, 0.001)
    }

    @Test
    fun getProductos_debe_manejar_lista_vacia() = runTestWithClient(
        createMockClient(responseBody = """{"productos": [], "total": 0}""")
    ) { client ->
        val response = client.get("http://test/api/productos")
        val listResponse = json.decodeFromString<ProductoListResponse>(response.bodyAsText())

        assertEquals(0, listResponse.total)
        assertTrue(listResponse.productos.isEmpty())
    }
}
