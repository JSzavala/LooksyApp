package com.example.Looksy.CrudTienda.Datos

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ApiException(val statusCode: Int, mensaje: String) : Exception(mensaje)

object AuthApiService {
    private const val BASE_URL = "http://192.168.1.30:3000"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = HttpClient {
        expectSuccess = false
        install(ContentNegotiation) {
            json(json)
        }
    }

    private suspend fun checkError(response: HttpResponse) {
        if (response.status.isSuccess()) return
        val text = response.bodyAsText()
        println("API Error ($BASE_URL${response.request.url}): $text")
        val errorMsg = try {
            json.decodeFromString<ApiErrorBody>(text).mensaje
        } catch (_: Exception) {
            "Error ${response.status.value}"
        }
        throw ApiException(response.status.value, errorMsg)
    }

    suspend fun login(correo: String, contrasena: String): AuthResponse {
        val response = client.post("$BASE_URL/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(correo, contrasena))
        }
        checkError(response)
        val text = response.bodyAsText()
        println("Login response: $text")
        return json.decodeFromString<AuthResponse>(text)
    }

    suspend fun register(nombre: String, correo: String, contrasena: String, rol: String): AuthResponse {
        val response = client.post("$BASE_URL/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(nombre, correo, contrasena, rol))
        }
        checkError(response)
        val text = response.bodyAsText()
        println("Register response: $text")
        return json.decodeFromString<AuthResponse>(text)
    }

    suspend fun createTienda(
        nombreTienda: String, direccion: String, contacto: String, idAdministrador: Int
    ): TiendaCreateResponse {
        val response = client.post("$BASE_URL/api/tiendas") {
            contentType(ContentType.Application.Json)
            setBody(CreateTiendaRequest(nombreTienda, direccion, contacto, idAdministrador))
        }
        checkError(response)
        val text = response.bodyAsText()
        println("CreateTienda response: $text")
        return json.decodeFromString<TiendaCreateResponse>(text)
    }

    suspend fun getTiendasByAdmin(idAdministrador: Int): TiendaListResponse {
        val response = client.get("$BASE_URL/api/tiendas")
        checkError(response)
        val text = response.bodyAsText()
        println("GetTiendas response: $text")
        return json.decodeFromString<TiendaListResponse>(text)
    }
}

@Serializable
data class ApiErrorBody(
    val error: String = "",
    val mensaje: String = ""
)

@Serializable
data class LoginRequest(
    val correo: String,
    val contrasena: String
)

@Serializable
data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rol: String
)

@Serializable
data class AuthResponse(
    val usuario: UsuarioDto,
    val token: String
)

@Serializable
data class UsuarioDto(
    val idUsuario: Int,
    val nombre: String,
    val correo: String,
    val rol: String
)

@Serializable
data class CreateTiendaRequest(
    val nombreTienda: String,
    val direccion: String,
    val contacto: String,
    val idAdministrador: Int
)

@Serializable
data class TiendaCreateResponse(
    val tienda: TiendaDto
)

@Serializable
data class TiendaListResponse(
    val tiendas: List<TiendaDto>,
    val total: Int
)

@Serializable
data class TiendaDto(
    val idTienda: Int,
    val nombreTienda: String,
    val direccion: String? = null,
    val contacto: String? = null,
    val idAdministrador: Int? = null
)
