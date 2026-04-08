Feature: Asignar un aspirante a una jornada y aula para realizar su examen.

Scenario: Asignar un aspirante a un aula durante una jornada de pruebas.

Given se tiene un servidor corriendo con la aplicación desplegada.
And existe un aspirante con una opción de carrera registrada.
And existe una prueba activa.
And esa prueba está asociada a una jornada.
And la jornada tiene un aula disponible.
When asigno al aspirante a la prueba en dicha jornada y aula.
Then verificar que el aspirante está asignado a la jornada y al "Aula 202".