package com.example.Looksy.SubirProducto
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.Looksy.CrudTienda.Datos.ProductoRepository
import com.example.Looksy.CrudTienda.Presentacion.Datos.ProductoTienda

class Funcionalidad_subirProducto {
    var idEdicion by mutableStateOf<Int?>(null)
    var nombrePrenda by mutableStateOf("")
    var precioPrenda by mutableStateOf("")
    var tallasSeleccionadas = mutableStateListOf<String>()
    var descripcionPrenda by mutableStateOf("")
    var stockPrenda by mutableStateOf("")
    var imagenPrenda by mutableStateOf("")

    fun cargarDatosParaEditar(producto: ProductoTienda) {
        idEdicion = producto.id
        nombrePrenda = producto.nombre
        precioPrenda = producto.precio.replace("$", "")
        tallasSeleccionadas.clear()
        tallasSeleccionadas.addAll(producto.tallasSeleccionadas)
        descripcionPrenda = producto.descricpion
        stockPrenda = producto.stock.toString()
    }
    fun publicar(navController: NavHostController) {
        if (nombrePrenda.isNotEmpty() && precioPrenda.isNotEmpty()) {

            // Creamos el objeto con los datos del formulario
            val nuevo = ProductoTienda(
                id = idEdicion?:(0..1000).random(),
                nombre = nombrePrenda,
                precio = "$$precioPrenda",
                tallasSeleccionadas = tallasSeleccionadas,
                descricpion = descripcionPrenda,
                stock = stockPrenda.toInt(),
                colorPlaceholder = Color.Gray // O el color que desees
            )
            if(idEdicion==null){
                ProductoRepository.agregarProducto(nuevo)
            }else{
                ProductoRepository.actualizarProducto(nuevo)
            }
            // Lo guardamos en el repositorio singleton
            //ProductoRepository.agregarProducto(nuevo)

            // Navegamos hacia atrás
            navController.popBackStack()
            // Aquí conectarás con tu API de Node.js/Prisma
            //Al rato la conectamos ntp
            //println("Subiendo $nombrePrenda a la base de datos MySQL...")
        } else {
            println("Error: Faltan datos por llenar")
        }
        navController.navigate("perfil");
    }


}