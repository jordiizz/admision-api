Feature: Crear un servicio para que los administradores puedan agregar nuevas opciones de respuesta al banco general de la plataforma.

Scenario: Crear un distractor de forma independiente

Given se tiene un servidor corriendo con la aplicación desplegada.
When envío la petición para crear un distractor con el valor "Ninguna de las anteriores"
Then al consultar el distractor recién creado su valor es "Ninguna de las anteriores"