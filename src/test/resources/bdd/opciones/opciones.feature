Feature: Asociar aspirantes a distintas opciones de carrera o curso por prioridad

Scenario: Crear perfil de aspirante y asociaciarlo a varias opciones de carrera o curso por prioridad
Given se tiene un servidor corriendo con la aplicación desplegada.
When puedo crear un aspirante
And puedo asociarle opciones de carrear por ejemplo I30515 y I30516
Then verificar las opciones de carrera asociadas al aspirante y su prioridad