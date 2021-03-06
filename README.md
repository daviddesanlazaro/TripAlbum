#TripAlbum

Esta aplicación para Android permite al usuario crear un álbum en el que mantener un registro de sus visitas a los diferentes lugares emblemáticos del país.
Funcionalidades:

- Consultar todos los lugares visitados y la información de cada visita realizada.
- Registrar nuevas visitas a un lugar, así como modificarlas y eliminarlas.
- Buscar nuevos lugares para visitar. Es posible filtrar por provincia y buscar por nombre. Se muestra la localización del lugar en un mapa y permite calcular la ruta desde la posición del usuario (por GPS).
- Añadir/eliminar lugares como favoritos.
- Añadir/eliminar usuarios a una lista de amigos, lo que permite acceder a su álbum para ver sus visitas.
- Menú de preferencias para personalizar las opciones de la aplicación.
- Layouts para la posición landscape.
- Disponible en español e inglés.

La información está almacenada en una API: https://github.com/daviddesanlazaro/TripAlbumAPI


*** IMPORTANTE ***

Para probar esta aplicación es necesario acceder como uno de los usuarios registrados en la API, pero todavía no se ha implementado ningún control de sesión. Así que de momento es necesario introducir el ID del usuario que se quiera utilizar directamente en el código. Esto se realiza en el método onCreate de la MainActivityView (línea 25).
