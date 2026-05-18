package com.example.Looksy

import com.example.Looksy.CrudTienda.Datos.ApiErrorBody
import com.example.Looksy.CrudTienda.Datos.AuthResponse
import com.example.Looksy.CrudTienda.Datos.CreateTiendaRequest
import com.example.Looksy.CrudTienda.Datos.LoginRequest
import com.example.Looksy.CrudTienda.Datos.RegisterRequest
import com.example.Looksy.CrudTienda.Datos.TiendaCreateResponse
import com.example.Looksy.CrudTienda.Datos.TiendaListResponse
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

class AuthApiServiceMockTest {

    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    private fun createMockClient(
        expectedPath: String,
        expectedMethod: HttpMethod,
        status: HttpStatusCode = HttpStatusCode.OK,
        responseBody: String = ""
    ): HttpClient {
        val mockEngine = MockEngine { request ->
            when {
                request.url.encodedPath == expectedPath && request.method == expectedMethod -> {
                    respond(
                        content = responseBody,
                        status = status,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> {
                    respond(
                        content = """{"error":"Not Found","mensaje":"Ruta no encontrada"}""",
                        status = HttpStatusCode.NotFound,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(this@AuthApiServiceMockTest.json)
            }
        }
    }

    @Test
    fun login_debe_deserializar_respuesta_exitosa() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/auth/login",
            expectedMethod = HttpMethod.Post,
            responseBody = """
                {
                    "usuario": {"idUsuario": 1, "nombre": "Admin", "correo": "admin@test.com", "rol": "tienda"},
                    "token": "jwt_token_123"
                }
            """.trimIndent()
        )
    ) { client ->
        val response = client.post("http://test/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest("admin@test.com", "pass123"))
        }
        val authResponse = json.decodeFromString<AuthResponse>(response.bodyAsText())

        assertEquals("jwt_token_123", authResponse.token)
        assertEquals(1, authResponse.usuario.idUsuario)
        assertEquals("Admin", authResponse.usuario.nombre)
        assertEquals("tienda", authResponse.usuario.rol)
    }

    @Test
    fun login_debe_manejar_error_401() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/auth/login",
            expectedMethod = HttpMethod.Post,
            status = HttpStatusCode.Unauthorized,
            responseBody = """{"error":"Unauthorized","mensaje":"Credenciales invalidas"}"""
        )
    ) { client ->
        val response = client.post("http://test/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest("wrong@test.com", "wrong"))
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
        val error = json.decodeFromString<ApiErrorBody>(response.bodyAsText())
        assertEquals("Credenciales invalidas", error.mensaje)
    }

    @Test
    fun register_debe_crear_usuario_exitosamente() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/auth/register",
            expectedMethod = HttpMethod.Post,
            responseBody = """
                {
                    "usuario": {"idUsuario": 2, "nombre": "Nuevo", "correo": "nuevo@test.com", "rol": "cliente"},
                    "token": "new_token_456"
                }
            """.trimIndent()
        )
    ) { client ->
        val response = client.post("http://test/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest("Nuevo", "nuevo@test.com", "pass123", "cliente"))
        }
        val authResponse = json.decodeFromString<AuthResponse>(response.bodyAsText())

        assertEquals("new_token_456", authResponse.token)
        assertEquals("cliente", authResponse.usuario.rol)
        assertEquals("Nuevo", authResponse.usuario.nombre)
    }

    @Test
    fun register_debe_manejar_error_de_usuario_duplicado() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/auth/register",
            expectedMethod = HttpMethod.Post,
            status = HttpStatusCode.Conflict,
            responseBody = """{"error":"Conflict","mensaje":"El correo ya esta registrado"}"""
        )
    ) { client ->
        val response = client.post("http://test/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest("Existente", "existente@test.com", "pass", "cliente"))
        }

        assertEquals(HttpStatusCode.Conflict, response.status)
        val error = json.decodeFromString<ApiErrorBody>(response.bodyAsText())
        assertEquals("El correo ya esta registrado", error.mensaje)
    }

    @Test
    fun createTienda_debe_crear_tienda_exitosamente() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/tiendas",
            expectedMethod = HttpMethod.Post,
            responseBody = """
                {
                    "tienda": {"idTienda": 1, "nombreTienda": "Mi Tienda", "direccion": "Calle 123", "contacto": "555-0000", "idAdministrador": 1}
                }
            """.trimIndent()
        )
    ) { client ->
        val response = client.post("http://test/api/tiendas") {
            contentType(ContentType.Application.Json)
            setBody(CreateTiendaRequest("Mi Tienda", "Calle 123", "555-0000", 1))
        }
        val tiendaResponse = json.decodeFromString<TiendaCreateResponse>(response.bodyAsText())

        assertEquals(1, tiendaResponse.tienda.idTienda)
        assertEquals("Mi Tienda", tiendaResponse.tienda.nombreTienda)
        assertEquals(1, tiendaResponse.tienda.idAdministrador)
    }

    @Test
    fun getTiendasByAdmin_debe_retornar_lista() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/tiendas",
            expectedMethod = HttpMethod.Get,
            responseBody = """
                {
                    "tiendas": [
                        {"idTienda": 1, "nombreTienda": "Tienda A", "direccion": "Dir A", "contacto": "555-1111", "idAdministrador": 1},
                        {"idTienda": 2, "nombreTienda": "Tienda B", "direccion": "Dir B", "contacto": "555-2222", "idAdministrador": 1}
                    ],
                    "total": 2
                }
            """.trimIndent()
        )
    ) { client ->
        val response = client.get("http://test/api/tiendas")
        val listResponse = json.decodeFromString<TiendaListResponse>(response.bodyAsText())

        assertEquals(2, listResponse.total)
        assertEquals("Tienda A", listResponse.tiendas[0].nombreTienda)
        assertEquals("Tienda B", listResponse.tiendas[1].nombreTienda)
    }

    @Test
    fun getTiendasByAdmin_debe_manejar_lista_vacia() = runTestWithClient(
        createMockClient(
            expectedPath = "/api/tiendas",
            expectedMethod = HttpMethod.Get,
            responseBody = """{"tiendas": [], "total": 0}"""
        )
    ) { client ->
        val response = client.get("http://test/api/tiendas")
        val listResponse = json.decodeFromString<TiendaListResponse>(response.bodyAsText())

        assertEquals(0, listResponse.total)
        assertEquals(0, listResponse.tiendas.size)
    }
}
