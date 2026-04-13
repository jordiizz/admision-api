Feature: Gestión del banco de preguntas por área
  Como administrador académico
  Quiero agregar nuevas preguntas al banco y categorizarlas por área de conocimiento
  Para mantener organizados los instrumentos de evaluación

Scenario: Crear una pregunta nueva y asociarla a un área existente
Given se tiene un servidor corriendo con la aplicación desplegada
And existe el área de conocimiento "Física" con el ID "20000000-0000-0000-0000-000000000001"
When envío la petición para crear la pregunta "¿Cuál es la unidad de medida de la fuerza en el SI?"
Then asocicio la pregunta creada a el area
And al consultar verifico que se haya realizado la relacion correctamente