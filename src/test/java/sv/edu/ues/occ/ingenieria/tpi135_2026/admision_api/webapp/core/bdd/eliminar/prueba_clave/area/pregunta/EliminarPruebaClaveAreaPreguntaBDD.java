package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.eliminar.prueba_clave.area.pregunta;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaDistractor;

import java.util.List;
import java.util.UUID;

public class EliminarPruebaClaveAreaPreguntaBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    PruebaClave pruebaClave;
    PruebaClaveArea pruebaClaveArea;

    @Given("se tiene un servidor corriendo con la aplicación desplegada")
    public void se_tiene_un_servidor_corriendo_con_la_aplicacion_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }

    @Given("que existe la clave {string} con ID {string} en la prueba con ID {string}")
    public void que_existe_la_clave_con_id_en_la_prueba_con_id(String nombreClave, String idPruebaClave, String idPrueba) {
        System.out.println("Buscando clave existente");

        int esperado = 200;

        Response respuesta = target.path("prueba")
            .path(idPrueba)
                .path("clave")
                .path(idPruebaClave)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        pruebaClave = respuesta.readEntity(PruebaClave.class);
        Assertions.assertNotNull(pruebaClave);
        Assertions.assertEquals(UUID.fromString(idPruebaClave), pruebaClave.getIdPruebaClave());
        Assertions.assertEquals(nombreClave, pruebaClave.getNombreClave());
        System.out.println("Clave: " + pruebaClave.getNombreClave());
    }

    @Given("que la clave tiene configurada el área {string} con ID {string}")
    public void que_la_clave_tiene_configurada_el_area_con_id(String nombreArea, String idArea) {
        System.out.println("Verificando area asociada a clave");

        int esperado = 200;

        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .path(idArea)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        pruebaClaveArea = respuesta.readEntity(PruebaClaveArea.class);
        Assertions.assertNotNull(pruebaClaveArea);
        Assertions.assertNotNull(pruebaClaveArea.getIdArea());
        Assertions.assertEquals(UUID.fromString(idArea), pruebaClaveArea.getIdArea().getIdArea());
        Assertions.assertEquals(nombreArea, pruebaClaveArea.getIdArea().getNombre());
        Assertions.assertNotNull(pruebaClaveArea.getIdPruebaClave());
        Assertions.assertEquals(pruebaClave.getIdPruebaClave(), pruebaClaveArea.getIdPruebaClave().getIdPruebaClave());
        System.out.println("Area: " + pruebaClaveArea.getIdArea().getNombre());
    }

    @Given("que el área tiene asignada la pregunta {string} con ID {string}")
    public void que_el_area_tiene_asignada_la_pregunta_con_id(String valorPregunta, String idPregunta) {
        System.out.println("Verificando pregunta asociada al area");

        int esperado = 200;

        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .path(pruebaClaveArea.getIdArea().getIdArea().toString())
                .path("pregunta")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        List<PruebaClaveAreaPregunta> preguntas = respuesta.readEntity(new GenericType<List<PruebaClaveAreaPregunta>>() {});
        Assertions.assertNotNull(preguntas);

        boolean encontrada = preguntas.stream().anyMatch(p -> p.getIdPregunta() != null
                && p.getIdPregunta().getIdPregunta() != null
                && p.getIdPregunta().getIdPregunta().equals(UUID.fromString(idPregunta))
                && valorPregunta.equals(p.getIdPregunta().getValor()));

        Assertions.assertTrue(encontrada);
        System.out.println("Pregunta encontrada: " + idPregunta);
    }

    @When("he eliminado todos los distractores de la pregunta con ID {string} del área {string} en la clave {string}")
    public void he_eliminado_todos_los_distractores_de_la_pregunta_con_id_del_area_en_la_clave(String idPregunta, String idArea, String idPruebaClave) {
        System.out.println("Eliminando distractores de la pregunta");

        int esperado = 204;

        Response respuestaDistractores = target.path("prueba_clave")
            .path(idPruebaClave)
            .path("area")
            .path(idArea)
            .path("pregunta")
            .path(idPregunta)
            .path("distractor")
            .request(MediaType.APPLICATION_JSON)
            .get();

        Assertions.assertNotNull(respuestaDistractores);
        Assertions.assertEquals(200, respuestaDistractores.getStatus());
        List<PruebaClaveAreaPreguntaDistractor> distractores = respuestaDistractores.readEntity(new GenericType<List<PruebaClaveAreaPreguntaDistractor>>() {});
        Assertions.assertNotNull(distractores);

        for (PruebaClaveAreaPreguntaDistractor distractor : distractores) {
            Assertions.assertNotNull(distractor);
            Assertions.assertNotNull(distractor.getIdDistractor());
            Assertions.assertNotNull(distractor.getIdDistractor().getIdDistractor());

            Response respuestaEliminarDistractor = target.path("prueba_clave")
                .path(idPruebaClave)
                .path("area")
                .path(idArea)
                .path("pregunta")
                .path(idPregunta)
                .path("distractor")
                .path(distractor.getIdDistractor().getIdDistractor().toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

            Assertions.assertNotNull(respuestaEliminarDistractor);
            Assertions.assertEquals(esperado, respuestaEliminarDistractor.getStatus());
        }
    }

    @When("elimino la pregunta con ID {string} del área {string} en la clave {string}")
    public void elimino_la_pregunta_con_id_del_area_en_la_clave(String idPregunta, String idArea, String idPruebaClave) {
        System.out.println("Eliminando relacion pregunta-area-clave");

        int esperado = 204;

        Response respuesta = target.path("prueba_clave")
                .path(idPruebaClave)
                .path("area")
                .path(idArea)
                .path("pregunta")
                .path(idPregunta)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
    }

    @Then("al consultar las preguntas del área con ID {string} en la clave {string}, verifico que la pregunta con ID {string} ya no existe")
    public void al_consultar_las_preguntas_del_area_con_id_en_la_clave_verifico_que_la_pregunta_con_id_ya_no_existe(String idArea, String idPruebaClave, String idPregunta) {
        System.out.println("Consultando preguntas del area para verificar eliminacion");

        Response respuesta = target.path("prueba_clave")
                .path(idPruebaClave)
                .path("area")
                .path(idArea)
                .path("pregunta")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        int status = respuesta.getStatus();
        Assertions.assertTrue(status == 200 || status == 404);

        if (status == 200) {
            List<PruebaClaveAreaPregunta> preguntas = respuesta.readEntity(new GenericType<List<PruebaClaveAreaPregunta>>() {});
            Assertions.assertNotNull(preguntas);

            boolean encontrada = preguntas.stream().anyMatch(p -> p.getIdPregunta() != null
                    && p.getIdPregunta().getIdPregunta() != null
                    && p.getIdPregunta().getIdPregunta().equals(UUID.fromString(idPregunta)));

            Assertions.assertFalse(encontrada);
        }
    }
}
