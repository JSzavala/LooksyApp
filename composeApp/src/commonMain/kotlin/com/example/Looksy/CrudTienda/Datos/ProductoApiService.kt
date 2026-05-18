package com.example.Looksy.CrudTienda.Datos

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object PrecioSerializer : KSerializer<Double> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Precio", PrimitiveKind.FLOAT)

    override fun deserialize(decoder: Decoder): Double {
        if (decoder is JsonDecoder) {
            val element = decoder.decodeJsonElement()
            return when (element) {
                is JsonPrimitive -> {
                    if (element.isString) element.content.toDoubleOrNull() ?: 0.0
                    else element.content.toDoubleOrNull() ?: 0.0
                }
                else -> 0.0
            }
        }
        return decoder.decodeDouble()
    }

    override fun serialize(encoder: Encoder, value: Double) {
        encoder.encodeDouble(value)
    }
}

object ProductoApiService {
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

    private fun HttpRequestBuilder.auth() {
        AuthManager.authHeader?.let { header(HttpHeaders.Authorization, it) }
    }

    private suspend fun checkResponse(response: HttpResponse) {
        if (response.status.isSuccess()) return
        val body = response.bodyAsText()
        val errorMsg = try {
            json.decodeFromString<ApiErrorBody>(body).mensaje
        } catch (_: Exception) {
            "Error ${response.status.value}"
        }
        throw ApiException(response.status.value, errorMsg)
    }

    private suspend inline fun <reified T> decode(response: HttpResponse): T {
        val text = response.bodyAsText()
        return json.decodeFromString<T>(text)
    }

    suspend fun getProductos(): ProductoListResponse {
        val response = client.get("$BASE_URL/api/productos") { auth() }
        checkResponse(response)
        return decode(response)
    }

    suspend fun getProductosByTienda(idTienda: Int): ProductosByTiendaResponse {
        val response = client.get("$BASE_URL/api/tiendas/$idTienda/productos") { auth() }
        checkResponse(response)
        return decode(response)
    }

    suspend fun createProducto(request: CreateProductoRequest): ProductoResponse {
        val response = client.post("$BASE_URL/api/productos") {
            auth()
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        checkResponse(response)
        return decode(response)
    }

    suspend fun updateProducto(idProducto: Int, request: UpdateProductoRequest): ProductoResponse {
        val response = client.put("$BASE_URL/api/productos/$idProducto") {
            auth()
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        checkResponse(response)
        return decode(response)
    }

    suspend fun deleteProducto(idProducto: Int): DeleteProductoResponse {
        val response = client.delete("$BASE_URL/api/productos/$idProducto") { auth() }
        checkResponse(response)
        return decode(response)
    }
}

@Serializable
data class ProductoTiendaDto(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String? = null,
    @Serializable(with = PrecioSerializer::class)
    val precio: Double,
    val stock: Int = 0,
    val disponible: Boolean = true,
    val idTienda: Int? = null,
    val tienda: TiendaInfoDto? = null,
    val etiquetas: List<ProductoEtiquetaDto>? = null
)

@Serializable
data class TiendaInfoDto(
    val nombreTienda: String
)

@Serializable
data class ProductoEtiquetaDto(
    val etiqueta: EtiquetaInfoDto
)

@Serializable
data class EtiquetaInfoDto(
    val idEtiqueta: Int,
    val nombre: String
)

@Serializable
data class ProductoListResponse(
    val productos: List<ProductoTiendaDto>,
    val total: Int
)

@Serializable
data class ProductoResponse(
    val producto: ProductoTiendaDto
)

@Serializable
data class ProductosByTiendaResponse(
    val tienda: TiendaInfoDto,
    val productos: List<ProductoTiendaDto>,
    val total: Int
)

@Serializable
data class CreateProductoRequest(
    val nombre: String,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int = 0,
    val disponible: Boolean = true,
    val idTienda: Int,
    @SerialName("etiquetas")
    val etiquetas: List<Int>? = null
)

@Serializable
data class UpdateProductoRequest(
    val nombre: String? = null,
    val descripcion: String? = null,
    val precio: Double? = null,
    val stock: Int? = null,
    val disponible: Boolean? = null,
    val idTienda: Int? = null,
    @SerialName("etiquetas")
    val etiquetas: List<Int>? = null
)

@Serializable
data class DeleteProductoResponse(
    val mensaje: String,
    val idProducto: Int
)
