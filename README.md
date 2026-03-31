# looksy-app/

|
+-- composeApp/                                   # Módulo principal de Compose Multiplatform
|   +-- src/
|   |   +-- commonMain/                           # Código compartido entre Android e iOS
|   |   |   +-- kotlin/
|   |   |   |   +-- com/
|   |   |   |       +-- example/
|   |   |   |           +-- looksy/
|   |   |   |               +-- App.kt            # Punto de entrada principal de la UI compartida
|   |   |   |               +-- Platform.kt       # Definiciones expect/actual para funcionalidades específicas
|   |   |   |               |
|   |   |   |               +-- core/             # Núcleo de la aplicación (código compartido global)
|   |   |   |               |   +-- di/           # Inyección de dependencias (Koin/Kodein)
|   |   |   |               |   |   +-- modulos.kt
|   |   |   |               |   |
|   |   |   |               |   +-- network/      # Cliente HTTP y configuración de red
|   |   |   |               |   |   +-- cliente-http.kt
|   |   |   |               |   |   +-- interceptores.kt
|   |   |   |               |   |   +-- manejo-errores.kt
|   |   |   |               |   |
|   |   |   |               |   +-- almacenamiento/ # Almacenamiento local
|   |   |   |               |   |   +-- preferencias.kt  # DataStore
|   |   |   |               |   |   +-- base-datos.kt    # SQLDelight
|   |   |   |               |   |   +-- cache.kt
|   |   |   |               |   |
|   |   |   |               |   +-- utilidades/    # Utilidades compartidas
|   |   |   |               |   |   +-- extensiones.kt
|   |   |   |               |   |   +-- validadores.kt
|   |   |   |               |   |   +-- formato.kt
|   |   |   |               |   |
|   |   |   |               |   +-- constantes/    # Constantes globales
|   |   |   |               |       +-- rutas.kt
|   |   |   |               |       +-- claves-api.kt
|   |   |   |               |       +-- temas.kt
|   |   |   |               |
|   |   |   |               +-- datos/             # Capa de datos (repositorios y fuentes)
|   |   |   |               |   +-- modelos/        # Modelos de datos compartidos
|   |   |   |               |   |   +-- usuario.modelo.kt
|   |   |   |               |   |   +-- producto.modelo.kt
|   |   |   |               |   |   +-- atuendo.modelo.kt
|   |   |   |               |   |   +-- tienda.modelo.kt
|   |   |   |               |   |   +-- pedido.modelo.kt
|   |   |   |               |   |   +-- etiqueta.modelo.kt
|   |   |   |               |   |
|   |   |   |               |   +-- fuentes/        # Fuentes de datos (remotas y locales)
|   |   |   |               |   |   +-- remotas/    # APIs y servicios remotos
|   |   |   |               |   |   |   +-- autenticacion.api.kt
|   |   |   |               |   |   |   +-- usuario.api.kt
|   |   |   |               |   |   |   +-- producto.api.kt
|   |   |   |               |   |   |   +-- atuendo.api.kt
|   |   |   |               |   |   |   +-- tienda.api.kt
|   |   |   |               |   |   |   +-- pedido.api.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- locales/    # Bases de datos locales
|   |   |   |               |   |       +-- dao/    # Objetos de acceso a datos
|   |   |   |               |   |       |   +-- usuario.dao.kt
|   |   |   |               |   |       |   +-- producto.dao.kt
|   |   |   |               |   |       |   +-- cache.dao.kt
|   |   |   |               |   |       |
|   |   |   |               |   |       +-- entidades/ # Entidades para BD local
|   |   |   |               |   |           +-- usuario.entidad.kt
|   |   |   |               |   |           +-- producto.entidad.kt
|   |   |   |               |   |
|   |   |   |               |   +-- repositorios/   # Implementaciones de repositorios
|   |   |   |               |       +-- autenticacion.repositorio.kt
|   |   |   |               |       +-- usuario.repositorio.kt
|   |   |   |               |       +-- producto.repositorio.kt
|   |   |   |               |       +-- atuendo.repositorio.kt
|   |   |   |               |       +-- tienda.repositorio.kt
|   |   |   |               |       +-- pedido.repositorio.kt
|   |   |   |               |       +-- busqueda.repositorio.kt
|   |   |   |               |       +-- imagenes.repositorio.kt
|   |   |   |               |
|   |   |   |               +-- dominio/            # Capa de dominio (casos de uso)
|   |   |   |               |   +-- casos-uso/       # Casos de uso específicos
|   |   |   |               |   |   +-- autenticacion/
|   |   |   |               |   |   |   +-- iniciar-sesion.caso.kt
|   |   |   |               |   |   |   +-- registrar-usuario.caso.kt
|   |   |   |               |   |   |   +-- registrar-proveedor.caso.kt
|   |   |   |               |   |   |   +-- cerrar-sesion.caso.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- producto/
|   |   |   |               |   |   |   +-- obtener-feed.caso.kt
|   |   |   |               |   |   |   +-- filtrar-productos.caso.kt
|   |   |   |               |   |   |   +-- obtener-detalle-producto.caso.kt
|   |   |   |               |   |   |   +-- obtener-productos-tienda.caso.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- atuendo/
|   |   |   |               |   |   |   +-- publicar-atuendo.caso.kt
|   |   |   |               |   |   |   +-- obtener-atuendos.caso.kt
|   |   |   |               |   |   |   +-- obtener-atuendos-usuario.caso.kt
|   |   |   |               |   |   |   +-- interactuar-atuendo.caso.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- tienda/
|   |   |   |               |   |   |   +-- obtener-tiendas.caso.kt
|   |   |   |               |   |   |   +-- obtener-detalle-tienda.caso.kt
|   |   |   |               |   |   |   +-- obtener-ubicacion-tienda.caso.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- usuario/
|   |   |   |               |   |   |   +-- obtener-perfil.caso.kt
|   |   |   |               |   |   |   +-- actualizar-perfil.caso.kt
|   |   |   |               |   |   |   +-- registrar-intereses.caso.kt
|   |   |   |               |   |   |   +-- obtener-intereses.caso.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- busqueda/
|   |   |   |               |   |   |   +-- buscar-productos.caso.kt
|   |   |   |               |   |   |   +-- buscar-tiendas.caso.kt
|   |   |   |               |   |   |   +-- buscar-atuendos.caso.kt
|   |   |   |               |   |   |
|   |   |   |               |   |   +-- pedido/
|   |   |   |               |   |       +-- crear-solicitud-compra.caso.kt
|   |   |   |               |   |       +-- obtener-pedidos.caso.kt
|   |   |   |               |   |       +-- obtener-detalle-pedido.caso.kt
|   |   |   |               |   |
|   |   |   |               |   +-- entidades/       # Entidades de dominio (reglas de negocio)
|   |   |   |               |       +-- usuario.entidad.kt
|   |   |   |               |       +-- producto.entidad.kt
|   |   |   |               |       +-- atuendo.entidad.kt
|   |   |   |               |       +-- tienda.entidad.kt
|   |   |   |               |
|   |   |   |               +-- presentacion/        # Capa de presentación (UI)
|   |   |   |                   +-- navegacion/       # Navegación
|   |   |   |                   |   +-- grafo-navegacion.kt
|   |   |   |                   |   +-- destinos.kt
|   |   |   |                   |
|   |   |   |                   +-- temas/            # Temas y estilos
|   |   |   |                   |   +-- colores.kt
|   |   |   |                   |   +-- tipografia.kt
|   |   |   |                   |   +-- tema.kt
|   |   |   |                   |
|   |   |   |                   +-- componentes/      # Componentes UI reutilizables
|   |   |   |                   |   +-- botones.kt
|   |   |   |                   |   +-- tarjetas.kt
|   |   |   |                   |   +-- campos-texto.kt
|   |   |   |                   |   +-- barras.kt
|   |   |   |                   |   +-- cuadriculas.kt
|   |   |   |                   |   +-- cargadores.kt
|   |   |   |                   |   +-- dialogs.kt
|   |   |   |                   |
|   |   |   |                   +-- pantallas/         # Pantallas principales por feature
|   |   |   |                   |   +-- barra-inferior/  # Barra de navegación inferior
|   |   |   |                   |   |   +-- datos/       # Modelos específicos
|   |   |   |                   |   |   +-- dominio/     # Lógica específica
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- barra-inferior.kt
|   |   |   |                   |   |       +-- viewmodel.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- autenticacion/   # Pantallas de login/registro
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-inicio-sesion.kt
|   |   |   |                   |   |       +-- pantalla-registro.kt
|   |   |   |                   |   |       +-- autenticacion.viewmodel.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- feed/            # Pantalla principal de feed
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-feed.kt
|   |   |   |                   |   |       +-- feed.viewmodel.kt
|   |   |   |                   |   |       +-- componentes-feed.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- listado-imagenes/ # Galería de imágenes/outfits
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-listado.kt
|   |   |   |                   |   |       +-- listado.viewmodel.kt
|   |   |   |                   |   |       +-- cuadricula-imagenes.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- buscador/        # Búsqueda y filtros
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-buscador.kt
|   |   |   |                   |   |       +-- buscador.viewmodel.kt
|   |   |   |                   |   |       +-- barra-busqueda.kt
|   |   |   |                   |   |       +-- filtros.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- perfil/          # Perfil de usuario
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-perfil.kt
|   |   |   |                   |   |       +-- pantalla-editar-perfil.kt
|   |   |   |                   |   |       +-- perfil.viewmodel.kt
|   |   |   |                   |   |       +-- componentes-perfil.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- producto/        # Detalle de producto
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-detalle-producto.kt
|   |   |   |                   |   |       +-- producto.viewmodel.kt
|   |   |   |                   |   |       +-- galeria-producto.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- tienda/          # Detalle de tienda
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-detalle-tienda.kt
|   |   |   |                   |   |       +-- tienda.viewmodel.kt
|   |   |   |                   |   |       +-- mapa-ubicacion.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- carrito/         # Carrito de compras
|   |   |   |                   |   |   +-- datos/
|   |   |   |                   |   |   +-- dominio/
|   |   |   |                   |   |   +-- presentacion/
|   |   |   |                   |   |       +-- pantalla-carrito.kt
|   |   |   |                   |   |       +-- carrito.viewmodel.kt
|   |   |   |                   |   |       +-- item-carrito.kt
|   |   |   |                   |   |
|   |   |   |                   |   +-- proveedor/       # Funcionalidades para proveedores
|   |   |   |                   |       +-- datos/
|   |   |   |                   |       +-- dominio/
|   |   |   |                   |       +-- presentacion/
|   |   |   |                   |           +-- pantalla-gestion-productos.kt
|   |   |   |                   |           +-- pantalla-agregar-producto.kt
|   |   |   |                   |           +-- pantalla-actualizar-inventario.kt
|   |   |   |                   |           +-- proveedor.viewmodel.kt
|   |   |   |                   |           +-- formulario-producto.kt
|   |   |   |                   |
|   |   |   |                   +-- viewmodels/          # ViewModels globales
|   |   |   |                       +-- sesion.viewmodel.kt
|   |   |   |                       +-- tema.viewmodel.kt
|   |   |   |
|   |   |   +-- resources/          # Recursos compartidos (imágenes, strings)
|   |   |       +-- drawable/
|   |   |       +-- values/
|   |   |       |   +-- strings.xml
|   |   |       |   +-- colors.xml
|   |   |       +-- fonts/
|   |   |
|   |   +-- androidMain/             # Código específico para Android
|   |   |   +-- kotlin/
|   |   |   |   +-- com/
|   |   |   |       +-- example/
|   |   |   |           +-- looksy/
|   |   |   |               +-- platform/
|   |   |   |               |   +-- camara.actual.kt
|   |   |   |               |   +-- notificaciones.actual.kt
|   |   |   |               |   +-- biometria.actual.kt
|   |   |   |               |   +-- mapas.actual.kt
|   |   |   |               |   +-- almacenamiento.actual.kt
|   |   |   |               |
|   |   |   |               +-- MainActivity.kt
|   |   |   |
|   |   |   +-- resources/
|   |   |
|   |   +-- iosMain/                 # Código específico para iOS
|   |       +-- kotlin/
|   |       |   +-- com/
|   |       |       +-- example/
|   |       |           +-- looksy/
|   |       |               +-- platform/
|   |       |                   +-- camara.actual.kt
|   |       |                   +-- notificaciones.actual.kt
|   |       |                   +-- biometria.actual.kt
|   |       |                   +-- mapas.actual.kt
|   |       |                   +-- almacenamiento.actual.kt
|   |       |
|   |       +-- resources/
|   |
+-- iosApp/                           # Proyecto nativo de iOS
|   +-- iosApp.xcodeproj/
|   +-- iosApp/
|
+-- gradle/
|   +-- libs.versions.toml            # Versiones de dependencias
|
+-- build.gradle.kts                   # Configuración principal
+-- settings.gradle.kts                 # Configuración de módulos
|
+-- pruebas/                            # Pruebas (análogo al backend)
|   +-- unitarias/
|   |   +-- dominio/
|   |   +-- datos/
|   |   +-- presentacion/
|   |
|   +-- integracion/
|   |   +-- repositorios/
|   |
|   +-- e2e/
|       +-- flujos/
|
+-- scripts/                            # Scripts de utilidad
|   +-- generar-iconos.sh
|   +-- build.sh
|
+-- .env                                 # Variables de entorno
+-- .env.ejemplo                         # Ejemplo de variables de entorno
+-- .gitignore                           # Archivos ignorados por git
+-- gradle.properties                     # Propiedades de Gradle
+-- README.md                             # Documentación del proyecto
