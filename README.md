# looksy-app/

├── composeApp/                                   # Módulo principal de Compose Multiplatform
│   ├── src/
│   │   ├── commonMain/                           # Código compartido entre Android e iOS
│   │   │   ├── kotlin/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── looksy/
│   │   │   │               ├── App.kt            # Punto de entrada principal de la UI compartida
│   │   │   │               ├── Platform.kt       # Definiciones expect/actual
│   │   │   │               │
│   │   │   │               ├── core/             # Núcleo de la aplicación
│   │   │   │               │   ├── di/           # Inyección de dependencias
│   │   │   │               │   │   └── modulos.kt
│   │   │   │               │   ├── network/      # Cliente HTTP
│   │   │   │               │   │   ├── cliente-http.kt
│   │   │   │               │   │   ├── interceptores.kt
│   │   │   │               │   │   └── manejo-errores.kt
│   │   │   │               │   ├── almacenamiento/ # Almacenamiento local
│   │   │   │               │   │   ├── preferencias.kt
│   │   │   │               │   │   ├── base-datos.kt
│   │   │   │               │   │   └── cache.kt
│   │   │   │               │   ├── utilidades/   # Utilidades compartidas
│   │   │   │               │   │   ├── extensiones.kt
│   │   │   │               │   │   ├── validadores.kt
│   │   │   │               │   │   └── formato.kt
│   │   │   │               │   └── constantes/   # Constantes globales
│   │   │   │               │       ├── rutas.kt
│   │   │   │               │       ├── claves-api.kt
│   │   │   │               │       └── temas.kt
│   │   │   │               │
│   │   │   │               ├── datos/            # Capa de datos
│   │   │   │               │   ├── modelos/      # Modelos compartidos
│   │   │   │               │   │   ├── usuario.modelo.kt
│   │   │   │               │   │   ├── producto.modelo.kt
│   │   │   │               │   │   ├── atuendo.modelo.kt
│   │   │   │               │   │   ├── tienda.modelo.kt
│   │   │   │               │   │   ├── pedido.modelo.kt
│   │   │   │               │   │   └── etiqueta.modelo.kt
│   │   │   │               │   │
│   │   │   │               │   ├── fuentes/      # Fuentes de datos
│   │   │   │               │   │   ├── remotas/  # APIs remotas
│   │   │   │               │   │   │   ├── autenticacion.api.kt
│   │   │   │               │   │   │   ├── usuario.api.kt
│   │   │   │               │   │   │   ├── producto.api.kt
│   │   │   │               │   │   │   ├── atuendo.api.kt
│   │   │   │               │   │   │   ├── tienda.api.kt
│   │   │   │               │   │   │   └── pedido.api.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   └── locales/  # Bases de datos locales
│   │   │   │               │   │       ├── dao/  # DAOs
│   │   │   │               │   │       │   ├── usuario.dao.kt
│   │   │   │               │   │       │   ├── producto.dao.kt
│   │   │   │               │   │       │   └── cache.dao.kt
│   │   │   │               │   │       │
│   │   │   │               │   │       └── entidades/ # Entidades BD
│   │   │   │               │   │           ├── usuario.entidad.kt
│   │   │   │               │   │           └── producto.entidad.kt
│   │   │   │               │   │
│   │   │   │               │   └── repositorios/ # Implementaciones repositorios
│   │   │   │               │       ├── autenticacion.repositorio.kt
│   │   │   │               │       ├── usuario.repositorio.kt
│   │   │   │               │       ├── producto.repositorio.kt
│   │   │   │               │       ├── atuendo.repositorio.kt
│   │   │   │               │       ├── tienda.repositorio.kt
│   │   │   │               │       ├── pedido.repositorio.kt
│   │   │   │               │       ├── busqueda.repositorio.kt
│   │   │   │               │       └── imagenes.repositorio.kt
│   │   │   │               │
│   │   │   │               ├── dominio/          # Capa de dominio
│   │   │   │               │   ├── casos-uso/    # Casos de uso
│   │   │   │               │   │   ├── autenticacion/
│   │   │   │               │   │   │   ├── iniciar-sesion.caso.kt
│   │   │   │               │   │   │   ├── registrar-usuario.caso.kt
│   │   │   │               │   │   │   ├── registrar-proveedor.caso.kt
│   │   │   │               │   │   │   └── cerrar-sesion.caso.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   ├── producto/
│   │   │   │               │   │   │   ├── obtener-feed.caso.kt
│   │   │   │               │   │   │   ├── filtrar-productos.caso.kt
│   │   │   │               │   │   │   ├── obtener-detalle-producto.caso.kt
│   │   │   │               │   │   │   └── obtener-productos-tienda.caso.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   ├── atuendo/
│   │   │   │               │   │   │   ├── publicar-atuendo.caso.kt
│   │   │   │               │   │   │   ├── obtener-atuendos.caso.kt
│   │   │   │               │   │   │   ├── obtener-atuendos-usuario.caso.kt
│   │   │   │               │   │   │   └── interactuar-atuendo.caso.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   ├── tienda/
│   │   │   │               │   │   │   ├── obtener-tiendas.caso.kt
│   │   │   │               │   │   │   ├── obtener-detalle-tienda.caso.kt
│   │   │   │               │   │   │   └── obtener-ubicacion-tienda.caso.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   ├── usuario/
│   │   │   │               │   │   │   ├── obtener-perfil.caso.kt
│   │   │   │               │   │   │   ├── actualizar-perfil.caso.kt
│   │   │   │               │   │   │   ├── registrar-intereses.caso.kt
│   │   │   │               │   │   │   └── obtener-intereses.caso.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   ├── busqueda/
│   │   │   │               │   │   │   ├── buscar-productos.caso.kt
│   │   │   │               │   │   │   ├── buscar-tiendas.caso.kt
│   │   │   │               │   │   │   └── buscar-atuendos.caso.kt
│   │   │   │               │   │   │
│   │   │   │               │   │   └── pedido/
│   │   │   │               │   │       ├── crear-solicitud-compra.caso.kt
│   │   │   │               │   │       ├── obtener-pedidos.caso.kt
│   │   │   │               │   │       └── obtener-detalle-pedido.caso.kt
│   │   │   │               │   │
│   │   │   │               │   └── entidades/   # Entidades de dominio
│   │   │   │               │       ├── usuario.entidad.kt
│   │   │   │               │       ├── producto.entidad.kt
│   │   │   │               │       ├── atuendo.entidad.kt
│   │   │   │               │       └── tienda.entidad.kt
│   │   │   │               │
│   │   │   │               └── presentacion/    # Capa de presentación
│   │   │   │                   ├── navegacion/
│   │   │   │                   │   ├── grafo-navegacion.kt
│   │   │   │                   │   └── destinos.kt
│   │   │   │                   │
│   │   │   │                   ├── temas/
│   │   │   │                   │   ├── colores.kt
│   │   │   │                   │   ├── tipografia.kt
│   │   │   │                   │   └── tema.kt
│   │   │   │                   │
│   │   │   │                   ├── componentes/ # UI reutilizables
│   │   │   │                   │   ├── botones.kt
│   │   │   │                   │   ├── tarjetas.kt
│   │   │   │                   │   ├── campos-texto.kt
│   │   │   │                   │   ├── barras.kt
│   │   │   │                   │   ├── cuadriculas.kt
│   │   │   │                   │   ├── cargadores.kt
│   │   │   │                   │   └── dialogs.kt
│   │   │   │                   │
│   │   │   │                   ├── pantallas/   # Pantallas por feature
│   │   │   │                   │   ├── barra-inferior/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── barra-inferior.kt
│   │   │   │                   │   │       └── viewmodel.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── autenticacion/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-inicio-sesion.kt
│   │   │   │                   │   │       ├── pantalla-registro.kt
│   │   │   │                   │   │       └── autenticacion.viewmodel.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── feed/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-feed.kt
│   │   │   │                   │   │       ├── feed.viewmodel.kt
│   │   │   │                   │   │       └── componentes-feed.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── listado-imagenes/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-listado.kt
│   │   │   │                   │   │       ├── listado.viewmodel.kt
│   │   │   │                   │   │       └── cuadricula-imagenes.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── buscador/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-buscador.kt
│   │   │   │                   │   │       ├── buscador.viewmodel.kt
│   │   │   │                   │   │       ├── barra-busqueda.kt
│   │   │   │                   │   │       └── filtros.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── perfil/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-perfil.kt
│   │   │   │                   │   │       ├── pantalla-editar-perfil.kt
│   │   │   │                   │   │       ├── perfil.viewmodel.kt
│   │   │   │                   │   │       └── componentes-perfil.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── producto/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-detalle-producto.kt
│   │   │   │                   │   │       ├── producto.viewmodel.kt
│   │   │   │                   │   │       └── galeria-producto.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── tienda/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-detalle-tienda.kt
│   │   │   │                   │   │       ├── tienda.viewmodel.kt
│   │   │   │                   │   │       └── mapa-ubicacion.kt
│   │   │   │                   │   │
│   │   │   │                   │   ├── carrito/
│   │   │   │                   │   │   ├── datos/
│   │   │   │                   │   │   ├── dominio/
│   │   │   │                   │   │   └── presentacion/
│   │   │   │                   │   │       ├── pantalla-carrito.kt
│   │   │   │                   │   │       ├── carrito.viewmodel.kt
│   │   │   │                   │   │       └── item-carrito.kt
│   │   │   │                   │   │
│   │   │   │                   │   └── proveedor/
│   │   │   │                   │       ├── datos/
│   │   │   │                   │       ├── dominio/
│   │   │   │                   │       └── presentacion/
│   │   │   │                   │           ├── pantalla-gestion-productos.kt
│   │   │   │                   │           ├── pantalla-agregar-producto.kt
│   │   │   │                   │           ├── pantalla-actualizar-inventario.kt
│   │   │   │                   │           ├── proveedor.viewmodel.kt
│   │   │   │                   │           └── formulario-producto.kt
│   │   │   │                   │
│   │   │   │                   └── viewmodels/ # ViewModels globales
│   │   │   │                       ├── sesion.viewmodel.kt
│   │   │   │                       └── tema.viewmodel.kt
│   │   │   │
│   │   │   └── resources/        # Recursos compartidos
│   │   │       ├── drawable/
│   │   │       ├── values/
│   │   │       │   ├── strings.xml
│   │   │       │   └── colors.xml
│   │   │       └── fonts/
│   │   │
│   │   ├── androidMain/           # Código específico Android
│   │   │   ├── kotlin/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── looksy/
│   │   │   │               ├── platform/
│   │   │   │               │   ├── camara.actual.kt
│   │   │   │               │   ├── notificaciones.actual.kt
│   │   │   │               │   ├── biometria.actual.kt
│   │   │   │               │   ├── mapas.actual.kt
│   │   │   │               │   └── almacenamiento.actual.kt
│   │   │   │               │
│   │   │   │               └── MainActivity.kt
│   │   │   │
│   │   │   └── resources/
│   │   │
│   │   └── iosMain/               # Código específico iOS
│   │       ├── kotlin/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── looksy/
│   │       │               └── platform/
│   │       │                   ├── camara.actual.kt
│   │       │                   ├── notificaciones.actual.kt
│   │       │                   ├── biometria.actual.kt
│   │       │                   ├── mapas.actual.kt
│   │       │                   └── almacenamiento.actual.kt
│   │       │
│   │       └── resources/
│   │
│   └── build/
│
├── iosApp/                         # Proyecto nativo de iOS
│   ├── iosApp.xcodeproj/
│   └── iosApp/
│
├── gradle/
│   └── libs.versions.toml          # Versiones de dependencias
│
├── pruebas/                         # Pruebas
│   ├── unitarias/
│   │   ├── dominio/
│   │   ├── datos/
│   │   └── presentacion/
│   │
│   ├── integracion/
│   │   └── repositorios/
│   │
│   └── e2e/
│       └── flujos/
│
├── scripts/                         # Scripts de utilidad
│   ├── generar-iconos.sh
│   └── build.sh
│
├── .env                             # Variables de entorno
├── .env.ejemplo                     # Ejemplo variables de entorno
├── .gitignore
├── build.gradle.kts                 # Configuración principal
├── gradle.properties
├── settings.gradle.kts              # Configuración de módulos
└── README.md                        # Este archivo
