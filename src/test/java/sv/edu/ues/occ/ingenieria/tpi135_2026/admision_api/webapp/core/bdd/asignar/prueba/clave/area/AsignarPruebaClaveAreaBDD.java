package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.asignar.prueba.clave.area;

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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AsignarPruebaClaveAreaBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    PruebaClave pruebaClave;
    Area area;

    @Given("se tiene un servidor corriendo con la aplicación desplegada")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }

    @Given("que existe una prueba con ID {string} asociada a una clave")
    public void que_existe_una_prueba_con_id_asociada_a_una_clave(String idPrueba) {
        System.out.println("Buscando una prueba y clave asociadas");

        int esperado = 200;

        Response respuesta = target.path("prueba")
                .path(idPrueba)
                .path("clave")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        List<PruebaClave> claves = respuesta.readEntity(new GenericType<List<PruebaClave>>() {});
        Assertions.assertNotNull(claves);
        Assertions.assertFalse(claves.isEmpty());
        pruebaClave = claves.getFirst();
        Assertions.assertNotNull(pruebaClave);
        System.out.println("Prueba: "+ pruebaClave.getIdPrueba());
    }

    @Given("crear el área de conocimiento {string}")
    public void crear_el_área_de_conocimiento(String nombre) {
        System.out.println("Verificando area de conocimiento");

        int esperado = 201;
        area = new Area();
        area.setNombre(nombre);


        Response respuesta = target.path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(area));

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("area/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: "+ id);
        area.setIdArea(id);
    }

    @When("defino que para esa clave el área de Matemáticas tendrá {string} preguntas y un peso de {string} por ciento")
    public void defino_que_para_esa_clave_el_área_de_matemáticas_tendrá_preguntas_y_un_peso_de_por_ciento(String cantidad, String porcentaje) {
        System.out.println("Asignando una pruebaCalveArea");
        int esperado = 201;
        PruebaClaveArea nuevo = new PruebaClaveArea();
        nuevo.setIdArea(area);
        nuevo.setPorcentaje(new BigDecimal(porcentaje));
        nuevo.setCantidad(Integer.parseInt(cantidad));

        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
    }

    @Then("al consultar el detalle de la clave puedo ver que el área de {string} está incluida correctamente")
    public void al_consultar_el_detalle_de_la_clave_puedo_ver_que_el_área_de_está_incluida_correctamente(String nombreArea) {
        System.out.println("Consultadno que se haya realizado la relación");
        int esperado = 200;

        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .path(area.getIdArea().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(esperado, respuesta.getStatus());
        PruebaClaveArea encontrado = respuesta.readEntity(PruebaClaveArea.class);
        Assertions.assertNotNull(encontrado);
        Assertions.assertNotNull(encontrado.getIdArea());
        Assertions.assertEquals(area.getIdArea(), encontrado.getIdArea().getIdArea());
        Assertions.assertEquals(nombreArea, encontrado.getIdArea().getNombre());
        System.out.println("nombreArea: "+ encontrado.getIdArea().getNombre());
    }
}
