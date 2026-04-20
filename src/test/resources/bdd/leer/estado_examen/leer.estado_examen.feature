Feature: Consulta de estado de admision de de aspirantes

Scenario: El aspirante obtiene una nota intermedia y queda en estado segunda ronda

Given se tiene una aplicacion desplegada en un servidor
When la aspirante "90000000-0000-0000-0000-000000000002" consulta las pruebas que ha realizado
And desea consultar su resultado para la prueba con id "30000000-0000-0000-0000-000000000002"
Then el sistema devuelve que su estado es "SEGUNDA_RONDA"
