# LooksyApp - Kotlin Multiplatform

LooksyApp es una aplicación móvil desarrollada con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform**, lo que permite compartir la lógica de negocio y la interfaz de usuario entre Android e iOS.

##  Arquitectura del Proyecto

La aplicación sigue una arquitectura de **Diseño por Capas (Clean Architecture)** adaptada para KMP, organizada por módulos de funcionalidades (features). Cada funcionalidad se divide en tres capas principales para asegurar la separación de responsabilidades:

1.  **Datos (Data):** Gestión de modelos de datos, implementaciones de repositorios y llamadas a APIs o bases de datos locales.
2.  **Dominio (Domain):** Contiene la lógica de negocio pura, entidades y casos de uso. Es independiente de la plataforma y de la interfaz de usuario.
3.  **Presentación (Presentation):** Contiene los componentes de UI (Composables) y la lógica de estado de la vista (ViewModels).

##  Estructura de Carpetas
--CommonMain: Codigo el cual comparten tanto la version de Android como la version de IOs de la app

Todas las siguientes carpetas se encuentran dentro de la dirección de: `composeApp/src/commonMain/kotlin/com/example/Looksy`

--- Buscador
-  **Buscador/Datos:** Modelos de búsqueda y gestión de datos/endpoints relacionados con la funcionalidad de búsqueda.
-   **Buscador/Dominio:** Lógica de negocio específica para el filtrado y procesamiento de búsquedas.
-   **Buscador/Presentacion:** Componentes visuales como y pantallas de resultados.

--- Login
-   **Login/Datos:** Modelos de autenticación y manejo de credenciales.
-   **Login/Dominio:** Reglas de validación de acceso y gestión de sesiones.
-   **Login/Presentacion:** Pantallas de inicio de sesión y registro.

--- Perfil
-   **Perfil/Datos:** Modelos del perfil de usuario y servicios de obtención de datos personales.
-   **Perfil/Dominio:** Lógica para la edición de perfil y gestión de preferencias.
-   **Perfil/Presentacion:** Interfaz de usuario para visualizar y editar el perfil del usuario.

--- ListadoImagenes
-   **ListadoImagenes/Datos:** Modelos de posts y servicios de carga de imágenes desde el servidor.
-   **ListadoImagenes/Dominio:** Lógica para el manejo de feeds y feeds de imágenes.
-   **ListadoImagenes/Presentacion:** Vistas de rejilla y listas para mostrar las imágenes en la aplicación.

--- BarraInferior
-   **BarraInferior/Datos:** Definición de los modelos para los ítems de navegación.
-   **BarraInferior/Dominio:** Lógica de navegación y definición de rutas.
-   **BarraInferior/Presentacion:** Implementación visual de la barra de navegación inferior.

--- Otros archivos raíz
-   **App.kt:** Punto de entrada principal de la UI compartida.
-   **Platform.kt:** Definiciones de `expect/actual` para funcionalidades específicas de cada plataforma.

--- Carpetas fuera de CommonMain
- IOsMain: Funcionalidades especificas para IOS
- AndroidMain: Funcionalidades Especificas para Android
