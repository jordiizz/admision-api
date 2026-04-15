Feature: Configuración de la estructura de evaluación
  Como administrador académico
  Quiero asignar áreas de conocimiento a las claves de las pruebas
  Para definir la cantidad de preguntas y el puntaje por cada sección

Scenario: Configurar una nueva sección de área para una clave de prueba
Given se tiene un servidor corriendo con la aplicación desplegada
And que existe una prueba con ID "30000000-0000-0000-0000-000000000001" asociada a una clave
And crear el área de conocimiento "Matemáticas"
When defino que para esa clave el área de Matemáticas tendrá "25" preguntas y un peso de "50.00" por ciento
Then al consultar el detalle de la clave puedo ver que el área de "Matemáticas" está incluida correctamente