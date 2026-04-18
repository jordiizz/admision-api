package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaClaveAreaPreguntaResourceST extends AbstractIntegrationTest{

    private final String RESOURCE_NAME_TIPO_PRUEBA = "tipo_prueba";
    private final String RESOURCE_NAME_PRUEBA = "prueba";
    private final String RESOURCE_NAME_PREGUNTA = "pregunta";
    private final String RESOURCE_NAME_AREA = "area";
    private final String RESOURCE_NAME_PRUEBA_CLAVE = "prueba_clave";
    private final String RESOURCE_NAME_CLAVE = "clave";

    TipoPrueba tipoPrueba = new TipoPrueba();
    Prueba prueba = new Prueba();
    Pregunta pregunta = new Pregunta();
    Pregunta pregunta2 = new Pregunta();
    Area area = new Area();
    PruebaClave pruebaClave = new PruebaClave();
    PruebaClaveArea pruebaClaveArea = new PruebaClaveArea();
    PruebaClaveAreaPregunta pruebaClaveAreaPregunta = new PruebaClaveAreaPregunta();
    PreguntaArea preguntaArea = new PreguntaArea();
    PruebaClaveAreaPregunta pruebaClaveAreaPregunta2 = new PruebaClaveAreaPregunta();

    private String idPruebaClave;
    private String idArea;
    private String idPregunta;
    private String idPregunta2;

    @Override
    public String getResourceName(){
        return "";
    }

    @BeforeEach
    public void setUp(){
        tipoPrueba.setValor("INGRESO_UNIVERSITARIO_PRIMERA_RONA");
        tipoPrueba.setActivo(true);

        prueba.setNombre("NUEVO_INGRESO");
        prueba.setIndicaciones("Completar prueba en 2 horas");
        prueba.setDuracion(120);
        prueba.setNotaAprobacion(new BigDecimal(60));
        prueba.setPuntajeMaximo(new BigDecimal(100));
        prueba.setFechaCreacion(OffsetDateTime.now());

        pregunta.setValor("¿Cuánto es 2 +2?");
        pregunta.setActivo(true);

        pregunta2.setValor("¿Cuánto es 2 + 5?");
        pregunta2.setActivo(true);

        area.setNombre("MATEMATICAS");
        area.setDescripcion("Ciencia de los números");
        area.setActivo(true);


        pruebaClave.setNombreClave("PRIMERA_CLAVE");
    }




    public void crearContexto(){
        Response respuestaTipoPrueba = target.path(RESOURCE_NAME_TIPO_PRUEBA)
                .request()
                .post(Entity.json(tipoPrueba));

        tipoPrueba = respuestaTipoPrueba.readEntity(TipoPrueba.class);

        prueba.setIdTipoPrueba(tipoPrueba);
        Response respuestaPrueba = target.path(RESOURCE_NAME_PRUEBA)
                .request()
                .post(Entity.json(prueba));

        prueba = respuestaPrueba.readEntity(Prueba.class);

        Response respuestaPregunta = target.path(RESOURCE_NAME_PREGUNTA)
                .request()
                .post(Entity.json(pregunta));

        pregunta = respuestaPregunta.readEntity(Pregunta.class);

        idPregunta = pregunta.getIdPregunta().toString();

        Response respuestaPregunta2 = target.path(RESOURCE_NAME_PREGUNTA)
                .request()
                .post(Entity.json(pregunta2));

        pregunta2 = respuestaPregunta2.readEntity(Pregunta.class);

        idPregunta2 = pregunta2.getIdPregunta().toString();

        Response respuestaArea = target.path(RESOURCE_NAME_AREA)
                .request()
                .post(Entity.json(area));

        area = respuestaArea.readEntity(Area.class);

        idArea = area.getIdArea().toString();

        pruebaClave.setIdPrueba(prueba);
        Response respuestaPruebaClave = target.path(RESOURCE_NAME_PRUEBA)
                .path(prueba.getIdPrueba().toString())
                .path(RESOURCE_NAME_CLAVE)
                .request()
                .post(Entity.json(pruebaClave));

        pruebaClave = respuestaPruebaClave.readEntity(PruebaClave.class);

        idPruebaClave = pruebaClave.getIdPruebaClave().toString();

        pruebaClaveArea.setIdArea(area);
        Response respuestaPruebaClaveArea = target.path(RESOURCE_NAME_PRUEBA_CLAVE)
                .path(pruebaClave.getIdPruebaClave().toString())
                .path(RESOURCE_NAME_AREA)
                .request()
                .post(Entity.json(pruebaClaveArea));

        pruebaClaveArea = respuestaPruebaClaveArea.readEntity(PruebaClaveArea.class);


        Response respuestaPreguntaArea = target.path(RESOURCE_NAME_PREGUNTA).path(idPregunta).path(RESOURCE_NAME_AREA).path(idArea).request()
                .post(null);

        Response respuestaPreguntaArea2 = target.path(RESOURCE_NAME_PREGUNTA).path(idPregunta2).path(RESOURCE_NAME_AREA).path(idArea).request()
                .post(null);
    }

    @Order(1)
    @Test
    public void testCrear(){
        crearContexto();
        pruebaClaveAreaPregunta.setIdPregunta(pregunta);
        pruebaClaveAreaPregunta.setPorcentaje(new BigDecimal(50));
        Response respuesta = target.path(RESOURCE_NAME_PRUEBA_CLAVE)
                .path(idPruebaClave)
                .path(RESOURCE_NAME_AREA)
                .path(idArea)
                .path(RESOURCE_NAME_PREGUNTA)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaClaveAreaPregunta));

        pruebaClaveAreaPregunta2.setIdPregunta(pregunta2);
        pruebaClaveAreaPregunta2.setPorcentaje(new BigDecimal(20));

        Response respuesta2 = target.path(RESOURCE_NAME_PRUEBA_CLAVE)
                .path(idPruebaClave)
                .path(RESOURCE_NAME_AREA)
                .path(idArea)
                .path(RESOURCE_NAME_PREGUNTA)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaClaveAreaPregunta2));

        assertEquals(Response.Status.CREATED.getStatusCode(), respuesta.getStatus());
        assertEquals(Response.Status.CREATED.getStatusCode(), respuesta2.getStatus());

        pruebaClaveAreaPregunta = respuesta.readEntity(PruebaClaveAreaPregunta.class);
        assertNotNull(pruebaClaveAreaPregunta);
        assertEquals(new BigDecimal(50), pruebaClaveAreaPregunta.getPorcentaje());
    }

    @Order(3)
    @Test
    public void testListar(){
        Response respuesta = target.path(RESOURCE_NAME_PRUEBA_CLAVE)
                .path(idPruebaClave)
                .path(RESOURCE_NAME_AREA)
                .path(idArea)
                .path(RESOURCE_NAME_PREGUNTA)
                .request(MediaType.APPLICATION_JSON)
                .get();
        List<PruebaClaveAreaPregunta> encontrados = respuesta.readEntity(new GenericType<List<PruebaClaveAreaPregunta>>() {});
        assertEquals(Response.Status.OK.getStatusCode(), respuesta.getStatus());
        assertTrue(encontrados.size() >= 2);
    }

    @Order(4)
    @Test
    public void testEliminar(){
        Response respuesta = target.path(RESOURCE_NAME_PRUEBA_CLAVE)
                .path(idPruebaClave)
                .path(RESOURCE_NAME_AREA)
                .path(idArea)
                .path(RESOURCE_NAME_PREGUNTA)
                .path(idPregunta)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), respuesta.getStatus());

    }

    @Order(5)
    @Test
    public void testActualizar(){
        pruebaClaveAreaPregunta2.setPorcentaje(new BigDecimal(10));
        Response respuesta = target.path(RESOURCE_NAME_PRUEBA_CLAVE)
                .path(idPruebaClave)
                .path(RESOURCE_NAME_AREA)
                .path(idArea)
                .path(RESOURCE_NAME_PREGUNTA)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(pruebaClaveAreaPregunta2));
        assertEquals(Response.Status.OK.getStatusCode(), respuesta.getStatus());

    }
}
