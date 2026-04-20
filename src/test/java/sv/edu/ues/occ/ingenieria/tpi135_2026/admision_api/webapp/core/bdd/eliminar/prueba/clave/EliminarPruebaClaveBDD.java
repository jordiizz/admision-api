package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.eliminar.prueba.clave;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;

import java.util.List;
import java.util.UUID;

public class EliminarPruebaClaveBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    Prueba prueba;
    PruebaClave pruebaClave;

    @Given("se tiene un servidor corriendo con la aplicación desplegada")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }
    @Given("que existe la prueba {string} con ID {string}")
    public void que_existe_la_prueba_con_id(String nombrePrueba, String idPrueba) {
        System.out.println("Buscando la prueba para eliminar clave");

        int esperado = 200;

        Response respuesta = target.path("prueba")
                .path(idPrueba)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        prueba = respuesta.readEntity(Prueba.class);
        Assertions.assertNotNull(prueba);
        Assertions.assertEquals(UUID.fromString(idPrueba), prueba.getIdPrueba());
        Assertions.assertEquals(nombrePrueba, prueba.getNombre());
        System.out.println("Prueba: " + prueba.getNombre());
    }
    @Given("que la prueba tiene asociada la clave {string} con ID {string}")
    public void que_la_prueba_tiene_asociada_la_clave_con_id(String nombreClave, String idPruebaClave) {
        System.out.println("Verificando clave asociada a la prueba");

        int esperado = 200;

        Response respuesta = target.path("prueba")
                .path(prueba.getIdPrueba().toString())
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
        Assertions.assertNotNull(pruebaClave.getIdPrueba());
        Assertions.assertEquals(prueba.getIdPrueba(), pruebaClave.getIdPrueba().getIdPrueba());
        System.out.println("Clave: " + pruebaClave.getNombreClave());
    }
    @When("elimino la clave con ID {string} de la prueba con ID {string}")
    public void elimino_la_clave_con_id_de_la_prueba_con_id(String idPruebaClave, String idPrueba) {
        System.out.println("Eliminando la clave de la prueba");

        int esperado = 204;

        Response respuesta = target.path("prueba")
                .path(idPrueba)
                .path("clave")
                .path(idPruebaClave)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
    }
    @Then("al consultar la lista de claves de la prueba con ID {string} verifico que la clave con ID {string} ya no existe")
    public void al_consultar_la_lista_de_claves_de_la_prueba_con_id_verifico_que_la_clave_con_id_ya_no_existe(String idPrueba, String idPruebaClave) {
        System.out.println("Consultando la lista de claves para verificar eliminación");

        Response respuesta = target.path("prueba")
                .path(idPrueba)
                .path("clave")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        int status = respuesta.getStatus();
        Assertions.assertTrue(status == 200 || status == 404);

        if (status == 200) {
            List<PruebaClave> claves = respuesta.readEntity(new GenericType<List<PruebaClave>>() {});
            Assertions.assertNotNull(claves);
            boolean encontrada = claves.stream()
                    .anyMatch(clave -> clave.getIdPruebaClave().equals(UUID.fromString(idPruebaClave)));
            Assertions.assertFalse(encontrada);
        }
    }
}
