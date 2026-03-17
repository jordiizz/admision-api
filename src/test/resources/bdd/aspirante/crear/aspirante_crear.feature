Feature: Crear un servicio para que los clientes puedan crear un perfil de aspirante y asociarlo a una opción de carrera o curso.

Scenario: Crear perfil de aspirante y asociación a una opción de carrera o curso.
Given se tiene un servidor corriendo con la aplicación desplegada.
When puedo crear un aspirante
And puedo asociarle a una opcion de carrera, por ejemplo I30515
Then puedo consultar el perfil del aspirante recien creado
And verificar la opcionn de carrera a la que fue asociado.