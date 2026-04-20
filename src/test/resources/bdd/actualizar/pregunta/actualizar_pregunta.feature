Feature: Gestión del banco de preguntas
  Como administrador del sistema
  Quiero poder actualizar las preguntas registradas
  Para corregir errores ortográficos o mejorar el enunciado del instrumento

  Scenario: Actualizar el enunciado de una pregunta exitosamente
    Given se tiene un servidor corriendo con la aplicación desplegada
    And que existe la pregunta "¿Fórmula de la fuerza?" con ID "50000000-0000-0000-0000-000000000001" en el sistema
    When actualizo la pregunta con ID "50000000-0000-0000-0000-000000000001" con el nuevo enunciado "¿Cuál es la fórmula matemática de la fuerza?"
    Then al consultar la pregunta con ID "50000000-0000-0000-0000-000000000001" verifico que el enunciado ahora es "¿Cuál es la fórmula matemática de la fuerza?"