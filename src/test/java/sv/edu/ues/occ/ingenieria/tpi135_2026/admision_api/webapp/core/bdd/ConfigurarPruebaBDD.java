package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ConfigurarPruebaBDD extends AbstractBDD {

    static Prueba prueba;
    static TipoPrueba tipo;
    static PruebaClave pruebaClave;
    static Area area;

    @Before
    public void setup() {
        initClient();
    }

    @Given("se tiene un servidor corriendo con la aplicación desplegada con rol de administrador.")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada_con_rol_de_administrador() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }

    @When("creo un nuevo tipo de prueba con el valor {string} y lo marco como activo.")
    public void creo_un_nuevo_tipo_de_prueba_con_el_valor_y_lo_marco_como_activo(String string) {
        // cliente diferente
        tipo = new TipoPrueba();
        tipo.setValor(string);
        tipo.setActivo(true);

        int esperado = 201;
        Response respuesta = target.path("tipo_prueba").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tipo));

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("tipo_prueba/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: "+ id);
        tipo.setIdTipoPrueba(id);
    }

    @When("creo una nueva área de conocimiento llamada {string} y la marco como activa.")
    public void creo_una_nueva_área_de_conocimiento_llamada_y_la_marco_como_activa(String string) {
        System.out.println("Creando Area");
        area = new Area();
        area.setNombre(string);
        area.setActivo(true);

        int esperado = 201;
        Response respuesta = target.path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(area));

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("area/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: "+ id);
        area.setIdArea(id);
    }

    @When("creo una nueva prueba llamada {string} asociada a este tipo de prueba recién creado, con un puntaje máximo de {double} y su respectiva nota de aprobación.")
    public void creo_una_nueva_prueba_llamada_asociada_a_este_tipo_de_prueba_recién_creado_con_un_puntaje_máximo_de_y_su_respectiva_nota_de_aprobación(String nombre, double max) {
        System.out.println("Creando prueba");
        prueba = new Prueba();
        prueba.setNombre(nombre);
        prueba.setIdTipoPrueba(tipo);
        prueba.setPuntajeMaximo(BigDecimal.valueOf(max));
        prueba.setNotaAprobacion(new BigDecimal("6.0"));
        prueba.setDuracion(90);
        prueba.setFechaCreacion(OffsetDateTime.now().withNano(0));

        int esperado = 201;

        Response respuesta = target.path("prueba")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(prueba));

        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("prueba/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: "+ id);
        prueba.setIdPrueba(id);
    }

    @When("le asocio una clave llamada {string}.")
    public void le_asocio_una_clave_llamada(String string) {
        System.out.println("Creando prueba Clave");
        pruebaClave = new PruebaClave();
        pruebaClave.setNombreClave(string);
        int esperado = 201;

        Response respuesta = target.path("prueba")
                .path(prueba.getIdPrueba().toString())
                .path("clave")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaClave));

        Assertions.assertEquals(esperado, respuesta.getStatus());
        String location = respuesta.getHeaderString("Location");
        Assertions.assertNotNull(location);
        UUID id = UUID.fromString(location.substring(location.lastIndexOf("/") + 1));
        System.out.println("ID: " + id);
        pruebaClave.setIdPruebaClave(id);
    }

    @When("vinculo el área Matemáticas a esta clave con un porcentaje del {int}%.")
    public void vinculo_el_área_a_esta_clave_con_un_porcentaje_del(Integer int1) {
        System.out.println("Asignando Area");
        PruebaClaveArea pcArea = new PruebaClaveArea();
        pcArea.setPorcentaje(BigDecimal.valueOf(int1));
        pcArea.setIdArea(area);
        int esperado = 201;

        Response respuesta = target.path("prueba_clave")
                .path(pruebaClave.getIdPruebaClave().toString())
                .path("area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pcArea));

        Assertions.assertEquals(esperado, respuesta.getStatus());

    }
    @Then("puedo consultar la prueba recién creada en el sistema.")
    public void puedo_consultar_la_prueba_recién_creada_en_el_sistema() {
        System.out.println("consultando prueba");

        int esperado = 200;

        Response respuesta = target.path("prueba")
                .path(prueba.getIdPrueba().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Prueba encontrado = respuesta.readEntity(Prueba.class);
        Assertions.assertEquals(0, prueba.getIdPrueba().compareTo(encontrado.getIdPrueba()));

    }
    @Then("verificar que la {string} tiene el área de {string} correctamente asignada.")
    public void verificar_que_la_tiene_el_área_de_correctamente_asignada(String nombreClave, String nombreArea) {

        UUID idPruebaClave = pruebaClave.getIdPruebaClave();
        UUID idArea = area.getIdArea();
        int esperado = 200;

        Response respuesta = target.path("prueba_clave")
                .path(idPruebaClave.toString())
                .path("area")
                .path(idArea.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        PruebaClaveArea encontrado = respuesta.readEntity(PruebaClaveArea.class);
        Assertions.assertEquals(nombreClave, encontrado.getIdPruebaClave().getNombreClave());
        Assertions.assertEquals(nombreArea, encontrado.getIdArea().getNombre());
        System.out.println("Area: "+ encontrado.getIdArea().getNombre());
        System.out.println("Clave: "+ encontrado.getIdPruebaClave().getNombreClave());
    }
}
