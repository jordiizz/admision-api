Feature: Planificación de espacios para evaluación
  Como coordinador logístico del proceso de admisión
  Quiero registrar nuevas jornadas y asignarles múltiples aulas físicas
  Para garantizar que haya suficiente capacidad instalada para los aspirantes

Scenario: Crear una jornada y habilitarle múltiples aulas en secuencia
Given se tiene un servidor corriendo con la aplicación desplegada
When registro una nueva jornada llamada "Jornada Domingo" con inicio "2026-04-19T08:00:00-06:00" y fin "2026-04-19T12:00:00-06:00"
And asigno el aula "AUL-101" a la jornada
And asigno una segunda aula "AUL-102" a la jornada
Then al verificar que las Aulas fueron correctamente asignadas