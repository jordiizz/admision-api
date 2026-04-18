package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.asignar.prueba_clave.area.pregunta.distractor;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsignarPruebaClaveAreaPreguntaDistractorBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    PruebaClave pruebaClave;
    List<PruebaClaveArea> areasGuardadas = new ArrayList<>();
    Map<String, String> mapaPreguntaArea = new HashMap<>();

    @Given("se tiene un servidor corriendo con la aplicación desplegada")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }

    @Given("que existe una prueba con ID {string} y su clave asociada con ID {string}")
    public void que_existe_una_prueba_con_id_y_su_clave_asociada_con_id(String idPrueba, String idPruebaClave) {
        System.out.println("Busco la pruebaClave");
        pruebaClave = new PruebaClave();
        int esperado = 200;

        Response respuesta = target.path("prueba")
                .path(idPrueba)
                .path("clave")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        List<PruebaClave> pruebaClaves = respuesta.readEntity(new GenericType<List<PruebaClave>>() {});
        for (PruebaClave clave : pruebaClaves) {
            if (idPruebaClave.equals(clave.getIdPruebaClave().toString())) {
                pruebaClave = clave;
            }
        }
        Assertions.assertNotNull(pruebaClave);
        Assertions.assertEquals(pruebaClave.getIdPrueba().getIdPrueba().toString(), idPrueba);
        System.out.println(pruebaClave);
    }

    @When("configuro el área {string} con ID {string} y capacidad {string} para esta clave")
    public void configuro_el_área_con_id_y_capacidad_para_esta_clave(String nombre, String idClave, String capacidad) {
        System.out.println("Configurando área: " + nombre);

        PruebaClaveArea pruebaClaveArea = new PruebaClaveArea();
        int esperado = 201;
        int esperadoBuscar = 200;

        Response respuestaBuscar = target.path("area")
                .path(idClave)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertNotNull(respuestaBuscar);
        Assertions.assertEquals(esperadoBuscar, respuestaBuscar.getStatus());

        Area area = respuestaBuscar.readEntity(Area.class);
        Assertions.assertNotNull(area);
        Assertions.assertEquals(area.getNombre(), nombre);
        Assertions.assertEquals(area.getIdArea().toString(), idClave);

        // Asignando pruebaClave
        pruebaClaveArea.setIdArea(area);
        pruebaClaveArea.setCantidad(Integer.parseInt(capacidad));
        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaClaveArea));

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());

        PruebaClaveArea areaCreada = respuesta.readEntity(PruebaClaveArea.class);
        pruebaClaveArea.setIdPruebaClave(areaCreada.getIdPruebaClave());

        // Guardamos el área en nuestra lista
        areasGuardadas.add(pruebaClaveArea);
    }

    @When("agrego la pregunta {string} con ID {string} al área con ID {string}")
    public void agrego_la_pregunta_con_id_al_área_con_id(String nombre, String idPregunta, String idArea) {
        System.out.println("Asignando la pregunta: " + nombre);
        int esperadoBuscar = 200;
        int esperado = 201;

        //Validamos que la pregunta realmente exista en la base de datos
        Response respuestaBuscar = target.path("pregunta")
                .path(idPregunta)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuestaBuscar);
        Assertions.assertEquals(esperadoBuscar, respuestaBuscar.getStatus());

        Pregunta preguntaExistente = respuestaBuscar.readEntity(Pregunta.class);
        Assertions.assertNotNull(preguntaExistente);
        Assertions.assertEquals(nombre, preguntaExistente.getValor());
        Assertions.assertEquals(idPregunta, preguntaExistente.getIdPregunta().toString());

        // Preparamos el objeto para la relación
        PruebaClaveAreaPregunta relacionPregunta = new PruebaClaveAreaPregunta();
        relacionPregunta.setPorcentaje(new BigDecimal(1));
        relacionPregunta.setIdPregunta(preguntaExistente);

        // Ejecutamos el POST
        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .path(idArea)
                .path("pregunta")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(relacionPregunta));

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());

        // 4. Guardamos la relación en el mapa para usarla después con los distractores
        mapaPreguntaArea.put(idPregunta, idArea);
    }

    @When("asocio el distractor {string} con ID {string} a la pregunta con ID {string}")
    public void asocio_el_distractor_con_id_a_la_pregunta_con_id(String nombre, String idDistractor, String idPregunta) {
        System.out.println("Asignando distractor: " + nombre);
        int esperadoBuscar = 200;
        int esperadoCrear = 201;

        // 1. Buscamos el idArea en el mapa (O(1) de complejidad)
        String idArea = mapaPreguntaArea.get(idPregunta);
        Assertions.assertNotNull(idArea);

        // 2. Validamos que el distractor exista en la BD
        Response respuestaBuscar = target.path("distractor")
                .path(idDistractor)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuestaBuscar);
        Assertions.assertEquals(esperadoBuscar, respuestaBuscar.getStatus());

        // 3. Ejecutamos el POST
        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .path(idArea)
                .path("pregunta")
                .path(idPregunta)
                .path("distractor")
                .path(idDistractor)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json("{}"));

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperadoCrear, respuesta.getStatus());
    }

    @Then("al consultar la estructura de la clave verifico que el árbol de datos contiene todas las áreas, preguntas y distractores")
    public void al_consultar_la_estructura_de_la_clave_verifico_que_el_árbol_de_datos_contiene_todas_las_áreas_preguntas_y_distractores() {
        System.out.println("Verificando la estructura completa del la prueba...");

        int totalPreguntasEncontradas = 0;
        int totalDistractoresEncontrados = 0;
        String idPruebaClave = pruebaClave.getIdPruebaClave().toString();

        // Iteramos sobre nuestra lista de áreas
        for (PruebaClaveArea area : areasGuardadas) {
            if (area == null) continue;

            String idArea = area.getIdArea().getIdArea().toString();

            // Consultamos las preguntas asociadas a esta área específica
            Response respuestaPreguntas = target.path("prueba_clave")
                    .path(idPruebaClave)
                    .path("area")
                    .path(idArea)
                    .path("pregunta")
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            Assertions.assertEquals(200, respuestaPreguntas.getStatus());

            List<PruebaClaveAreaPregunta> preguntasObtenidas = respuestaPreguntas.readEntity(new GenericType<List<PruebaClaveAreaPregunta>>() {});
            Assertions.assertNotNull(preguntasObtenidas);
            totalPreguntasEncontradas += preguntasObtenidas.size();

            // Iteramos sobre las preguntas encontradas para buscar sus distractores
            for (PruebaClaveAreaPregunta pregunta : preguntasObtenidas) {
                String idPregunta = pregunta.getIdPregunta().getIdPregunta().toString();

                Response respuestaDistractores = target.path("prueba_clave")
                        .path(idPruebaClave)
                        .path("area")
                        .path(idArea)
                        .path("pregunta")
                        .path(idPregunta)
                        .path("distractor")
                        .request(MediaType.APPLICATION_JSON)
                        .get();

                Assertions.assertEquals(200, respuestaDistractores.getStatus());

                List<PruebaClaveAreaPreguntaDistractor> distractoresObtenidos = respuestaDistractores.readEntity(new GenericType<List<PruebaClaveAreaPreguntaDistractor>>() {});
                Assertions.assertNotNull(distractoresObtenidos);

                totalDistractoresEncontrados += distractoresObtenidos.size();
            }
        }

        System.out.println("Total preguntas encontradas: " + totalPreguntasEncontradas);
        System.out.println("Total distractores encontrados: " + totalDistractoresEncontrados);

        Assertions.assertEquals(4, totalPreguntasEncontradas);
        Assertions.assertEquals(7, totalDistractoresEncontrados);
    }
}