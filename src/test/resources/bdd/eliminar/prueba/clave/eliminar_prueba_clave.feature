Feature: Gestión de la estructura de las pruebas
  Como administrador del sistema
  Quiero poder eliminar elementos asociados a una prueba
  Para corregir la configuración del instrumento de evaluación antes de su aplicación

  Scenario: Eliminar una clave asociada a una prueba exitosamente
    Given se tiene un servidor corriendo con la aplicación desplegada
    And que existe la prueba "Prueba de Aptitud 2026" con ID "30000000-0000-0000-0000-000000000001"
    And que la prueba tiene asociada la clave "Clave BDD Cascada" con ID "50000000-0000-0000-0000-000000000001"
    When elimino la clave con ID "50000000-0000-0000-0000-000000000001" de la prueba con ID "30000000-0000-0000-0000-000000000001"
    Then al consultar la lista de claves de la prueba con ID "30000000-0000-0000-0000-000000000001" verifico que la clave con ID "50000000-0000-0000-0000-000000000001" ya no existe