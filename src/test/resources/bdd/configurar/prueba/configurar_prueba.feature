Feature: Crear un servicio para que los administradores puedan configurar una prueba, asignando sus claves, áreas y preguntas.

Scenario: Configurar una nueva prueba con su clave y área de conocimiento.

Given se tiene un servidor corriendo con la aplicación desplegada con rol de administrador.
When creo un nuevo tipo de prueba con el valor "Ingreso a Universidad" y lo marco como activo.
And creo una nueva área de conocimiento llamada "Matemáticas" y la marco como activa.
And creo una nueva prueba llamada "Examen de Admisión 2026" asociada a este tipo de prueba recién creado, con un puntaje máximo de 10.0 y su respectiva nota de aprobación.
And le asocio una clave llamada "Clave A".
And vinculo el área Matemáticas a esta clave con un porcentaje del 50%.
Then puedo consultar la prueba recién creada en el sistema.
And verificar que la "Clave A" tiene el área de "Matemáticas" correctamente asignada.