Feature: Configuración en el banco de pruebas
  Como administrador del sistema
  Quiero estructurar las áreas, preguntas y distractores de una clave
  Para tener el instrumento de evaluación listo para su aplicación

  Scenario: Configurar múltiples áreas, preguntas y distractores en cascada paso a paso
    Given se tiene un servidor corriendo con la aplicación desplegada
    And que existe una prueba con ID "30000000-0000-0000-0000-000000000001" y su clave asociada con ID "50000000-0000-0000-0000-000000000001"

    # ================= Áreas =================
    When configuro el área "Física" con ID "20000000-0000-0000-0000-000000000001" y capacidad "10" para esta clave
    And configuro el área "Matemática" con ID "20000000-0000-0000-0000-000000000002" y capacidad "15" para esta clave

    # ================= Preguntas =================
    And agrego la pregunta "¿Qué es la inercia?" con ID "50000000-0000-0000-0000-000000000002" al área con ID "20000000-0000-0000-0000-000000000001"
    And agrego la pregunta "¿Cuál es la constante de la gravedad?" con ID "50000000-0000-0000-0000-000000000003" al área con ID "20000000-0000-0000-0000-000000000001"
    And agrego la pregunta "¿Cuál es la derivada de x^2?" con ID "50000000-0000-0000-0000-000000000004" al área con ID "20000000-0000-0000-0000-000000000002"
    And agrego la pregunta "¿Qué es un número primo?" con ID "50000000-0000-0000-0000-000000000005" al área con ID "20000000-0000-0000-0000-000000000002"

    # ================= Distractores =================
    And asocio el distractor "Resistencia al cambio de estado" con ID "60000000-0000-0000-0000-000000000003" a la pregunta con ID "50000000-0000-0000-0000-000000000002"
    And asocio el distractor "Una fuerza magnética" con ID "60000000-0000-0000-0000-000000000004" a la pregunta con ID "50000000-0000-0000-0000-000000000002"
    And asocio el distractor "Ninguna de las anteriores" con ID "60000000-0000-0000-0000-000000000005" a la pregunta con ID "50000000-0000-0000-0000-000000000002"

    And asocio el distractor "9.8 m/s^2" con ID "60000000-0000-0000-0000-000000000006" a la pregunta con ID "50000000-0000-0000-0000-000000000003"
    And asocio el distractor "3.1416" con ID "60000000-0000-0000-0000-000000000007" a la pregunta con ID "50000000-0000-0000-0000-000000000003"

    And asocio el distractor "2x" con ID "60000000-0000-0000-0000-000000000008" a la pregunta con ID "50000000-0000-0000-0000-000000000004"
    And asocio el distractor "x" con ID "60000000-0000-0000-0000-000000000009" a la pregunta con ID "50000000-0000-0000-0000-000000000004"

    # ================= Verificación =================
    Then al consultar la estructura de la clave verifico que el árbol de datos contiene todas las áreas, preguntas y distractores