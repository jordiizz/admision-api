Feature: Gestion de la estructura de las claves de prueba
  Como administrador del sistema
  Quiero poder remover preguntas especificas de las areas de una clave
  Para ajustar el contenido del examen segun los requerimientos academicos

  Scenario: Eliminar la relacion de una pregunta con un area dentro de una clave
    Given se tiene un servidor corriendo con la aplicación desplegada
    And que existe la clave "Clave Base" con ID "40000000-0000-0000-0000-000000000001" en la prueba con ID "30000000-0000-0000-0000-000000000001"
    And que la clave tiene configurada el área "Física" con ID "20000000-0000-0000-0000-000000000001"
    And que el área tiene asignada la pregunta "¿Fórmula de la fuerza?" con ID "50000000-0000-0000-0000-000000000001"
    When he eliminado todos los distractores de la pregunta con ID "50000000-0000-0000-0000-000000000001" del área "20000000-0000-0000-0000-000000000001" en la clave "40000000-0000-0000-0000-000000000001"
    And elimino la pregunta con ID "50000000-0000-0000-0000-000000000001" del área "20000000-0000-0000-0000-000000000001" en la clave "40000000-0000-0000-0000-000000000001"
    Then al consultar las preguntas del área con ID "20000000-0000-0000-0000-000000000001" en la clave "40000000-0000-0000-0000-000000000001", verifico que la pregunta con ID "50000000-0000-0000-0000-000000000001" ya no existe
