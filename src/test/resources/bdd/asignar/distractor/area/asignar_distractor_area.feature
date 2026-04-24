Feature: Organización del banco general de distractores
  Como administrador del banco de preguntas
  Quiero asociar distractores a diferentes áreas de conocimiento
  Para mantener un repositorio ordenado y facilitar la construcción de pruebas

  Scenario: Asociar un distractor a un área de conocimiento exitosamente
    Given se tiene un servidor corriendo con la aplicación desplegada

    And que existe el distractor "F = m * a" con ID "60000000-0000-0000-0000-000000000001" en el catálogo
    And que existe el área "Matemática" con ID "20000000-0000-0000-0000-000000000002"

    When asocio el distractor con ID "60000000-0000-0000-0000-000000000001" al área con ID "20000000-0000-0000-0000-000000000002"

    Then al consultar las áreas del distractor con ID "60000000-0000-0000-0000-000000000001", verifico que está vinculado al área con ID "20000000-0000-0000-0000-000000000002"