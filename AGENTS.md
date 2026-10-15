# Proyecto Stocks

Simulador empresarial multijugador.

## Tecnologías
- Backend: Java 17, Spring Boot
- Frontend: Angular
- Base de datos: PostgreSQL
- API REST

## Arquitectura
- Controller -> Service -> Repository
- DTOs para comunicación API
- JPA/Hibernate para persistencia
- Separar lógica de negocio de controladores

## Reglas para Codex
- Modificar únicamente los archivos necesarios.
- No refactorizar código ajeno a la tarea.
- Los controladores no deben llamar directamente a repositories; deben delegar en services.
- Reutilizar clases y servicios existentes.
- No añadir dependencias innecesarias.
- No utilizar `record` para DTOs; usar clases normales con Lombok.
- Nombrar los DTOs de salida o comunicación con el sufijo `Dto` (`UserDto`, etc.), no `Response`.
- Nombrar los cuerpos de entrada de la API con el sufijo `Request` (`LoginRequest`, `RegisterRequest`).
- Nombrar los modelos específicos de formularios con el sufijo `Form` (`LoginForm`, `RegisterForm`).
- Explicar brevemente las clases, métodos o líneas relevantes que no sean obvias cuando se añada o modifique código, indicando qué problema resuelven y por qué se elige esa solución.
- Evitar comentarios sobre código trivial o comentarios excesivos dentro de los archivos.
- Evitar explicaciones largas.
- Mostrar un resumen breve de los cambios.
- No ejecutar toda la batería de tests salvo necesidad.
- Preguntar antes de realizar cambios arquitectónicos.
