package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AspiranteResourceST extends AbstractIntegrationTest {

    private final String RESOURCE_NAME_ASPIRANTE = "aspirante";
    private final String RESOURCE_NAME_TIPO_PRUEBA = "tipo_prueba";
    private final String RESORUCE_NAME_PRUEBA = "prueba";
    private final String  RESOURCE_NAME_JORNADA = "jornada";
    private final String RESOURCE_NAME_AULA = "aula";
    private final String RESOURCE_NAME_ASPIRANTE_OPCION = "aspirante_opcion";
    private final String RESORCE_NAME_OPCION = "opcion";
    private final String RESOURCE_NAME_EXAMEN = "examen";
    private final String RESOURCE_NAME_CLAVE = "clave";

    TipoPrueba tipoPrueba = new TipoPrueba();
    UUID idTipoPrueba;
    Prueba prueba = new Prueba();
    UUID idPrueba;
    Jornada jornada = new Jornada();
    UUID idJornada;
    JornadaAula jornadaAula = new JornadaAula();
    String idAula = UUID.randomUUID().toString();
    Aspirante aspirante =  new Aspirante();
    UUID idAspirante;
    AspiranteOpcion aspiranteOpcion = new AspiranteOpcion();
    UUID idAspiranteOpcion;
    String idOpcion = UUID.randomUUID().toString();
    PruebaJornada pruebaJornada =  new PruebaJornada();
    PruebaJornadaAulaAspiranteOpcion pruebaJornadaAulaAspiranteOpcion = new PruebaJornadaAulaAspiranteOpcion();
    PruebaJornadaAulaAspiranteOpcionExamen pruebaJornadaAulaAspiranteOpcionExamen;
    PruebaClave pruebaClave = new PruebaClave();
    UUID idPruebaClave;


    @Override
    public String getResourceName() {
        return "";
    }

    @BeforeEach
    public void setUp() {
        tipoPrueba.setValor("INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
        tipoPrueba.setActivo(true);

        prueba.setIndicaciones("Indicaciones");
        prueba.setDuracion(120);
        prueba.setPuntajeMaximo(new BigDecimal(100));
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setNombre("NUEVO_INGRESO_2026");
        prueba.setNotaAprobacion(new BigDecimal(50));

        jornada.setFechaInicio(OffsetDateTime.now().plusDays(1));
        jornada.setFechaFin(OffsetDateTime.now().plusDays(2));
        jornada.setNombre("JORNADA_VESPERTINA_2026");

        jornadaAula.setIdAula(idAula);
        jornadaAula.setIdJornada(jornada);

        aspirante.setNombres("Jose");
        aspirante.setDocumentoIdentidad("111111-1");
        aspirante.setCorreo("jose@email.com");
        aspirante.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        aspirante.setApellidos("López");
        aspirante.setFechaCreacion(OffsetDateTime.now());

        aspiranteOpcion.setPrioridad(1);
        aspiranteOpcion.setFechaCreacion(OffsetDateTime.now());
        aspiranteOpcion.setIdAspirante(aspirante);
        aspiranteOpcion.setIdOpcion(idOpcion);

        pruebaClave.setNombreClave("PRIMERA_CLAVE");

    }

    public void crearContextoExamen(){
        Response respuestaTipoPrueba = target.path(RESOURCE_NAME_TIPO_PRUEBA)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tipoPrueba));
        idTipoPrueba = respuestaTipoPrueba.readEntity(TipoPrueba.class).getIdTipoPrueba();
        tipoPrueba.setIdTipoPrueba(idTipoPrueba);
        prueba.setIdTipoPrueba(tipoPrueba);
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaTipoPrueba.getStatus());

        Response respuestaPrueba = target.path(RESORUCE_NAME_PRUEBA)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(prueba));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaPrueba.getStatus());
        idPrueba = respuestaPrueba.readEntity(Prueba.class).getIdPrueba();
        prueba.setIdPrueba(idPrueba);

        pruebaClave.setIdPrueba(prueba);
        Response respuestaPruebaClave = target.path(RESORUCE_NAME_PRUEBA)
                .path(idPrueba.toString())
                .path(RESOURCE_NAME_CLAVE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaClave));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaPruebaClave.getStatus());
        idPruebaClave = respuestaPruebaClave.readEntity(PruebaClave.class).getIdPruebaClave();
        pruebaClave.setIdPruebaClave(idPruebaClave);

        Response respuestaJornada = target.path(RESOURCE_NAME_JORNADA)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jornada));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaJornada.getStatus());
        idJornada =respuestaJornada.readEntity(Jornada.class).getIdJornada();
        jornada.setIdJornada(idJornada);

        jornadaAula.setIdJornada(jornada);
        Response respuestaJornadaAula = target.path(RESOURCE_NAME_JORNADA)
                .path(idJornada.toString())
                .path(RESOURCE_NAME_AULA)
                .path(idAula)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jornadaAula));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaJornadaAula.getStatus());

        Response respuestaAspirante = target.path(RESOURCE_NAME_ASPIRANTE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(aspirante));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaAspirante.getStatus());
        idAspirante = respuestaAspirante.readEntity(Aspirante.class).getIdAspirante();


        Response respuestaAspiranteOpcion = target.path(RESOURCE_NAME_ASPIRANTE)
                .path(idAspirante.toString())
                .path(RESORCE_NAME_OPCION)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(aspiranteOpcion));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaAspiranteOpcion.getStatus());
        idAspiranteOpcion = respuestaAspiranteOpcion.readEntity(AspiranteOpcion.class).getIdAspiranteOpcion();
        aspiranteOpcion.setIdAspiranteOpcion(idAspiranteOpcion);

        pruebaJornada.setIdJornada(jornada);
        pruebaJornada.setIdPrueba(prueba);
        Response respuestaPruebaJornada = target.path(RESORUCE_NAME_PRUEBA)
                .path(idPrueba.toString())
                .path(RESOURCE_NAME_JORNADA)
                .path(idJornada.toString())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaJornada));

        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaPruebaJornada.getStatus());

        pruebaJornadaAulaAspiranteOpcion.setIdAula(idAula);
        pruebaJornadaAulaAspiranteOpcion.setIdPrueba(prueba);
        pruebaJornadaAulaAspiranteOpcion.setActivo(true);
        pruebaJornadaAulaAspiranteOpcion.setIdJornada(jornada);
        pruebaJornadaAulaAspiranteOpcion.setFecha(OffsetDateTime.now());
        pruebaJornadaAulaAspiranteOpcion.setIdAspiranteOpcion(aspiranteOpcion);

        Response respuestaPruebaJornadaAulaAspiranteOpcion = target.path(RESORUCE_NAME_PRUEBA)
                .path(idPrueba.toString())
                .path(RESOURCE_NAME_JORNADA)
                .path(idJornada.toString())
                .path(RESOURCE_NAME_AULA)
                .path(idAula)
                .path(RESOURCE_NAME_ASPIRANTE_OPCION)
                        .request(MediaType.APPLICATION_JSON)
                         .post(Entity.json(pruebaJornadaAulaAspiranteOpcion));
        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaPruebaJornadaAulaAspiranteOpcion.getStatus());


        pruebaJornadaAulaAspiranteOpcionExamen = new PruebaJornadaAulaAspiranteOpcionExamen(prueba, jornada, idAula, aspiranteOpcion );
        pruebaJornadaAulaAspiranteOpcionExamen.setIdPruebaClave(pruebaClave);
        pruebaJornadaAulaAspiranteOpcionExamen.setFechaResultado(OffsetDateTime.now());
        pruebaJornadaAulaAspiranteOpcionExamen.setResultado(new BigDecimal(70));

        Response respuestaPruebaJornadaAulaAspiranteOpcionExamen = target.path(RESORUCE_NAME_PRUEBA)
                .path(idPrueba.toString())
                .path(RESOURCE_NAME_JORNADA)
                .path(idJornada.toString())
                .path(RESOURCE_NAME_AULA)
                .path(idAula)
                .path(RESOURCE_NAME_ASPIRANTE_OPCION)
                .path(idAspiranteOpcion.toString())
                .path(RESOURCE_NAME_EXAMEN)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pruebaJornadaAulaAspiranteOpcionExamen));

        assertEquals(Response.Status.CREATED.getStatusCode(), respuestaPruebaJornadaAulaAspiranteOpcionExamen.getStatus());

    }

    @Order(1)
    @Test
    public void buscarPorRangoTest() {
        System.out.println("buscarPorRango en AspiranteResource");

        Response respuesta = target.path(RESOURCE_NAME_ASPIRANTE)
                .queryParam("first", 0)
                .queryParam("max", 50)
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(ResponseHeaders.TOTAL_RECORDS.toString()));
        List<Aspirante> registros = respuesta.readEntity(new GenericType<List<Aspirante>>() {});
        Assertions.assertNotNull(registros);
    }

    @Order(2)
    @Test
    public void buscarPorRangoParametrosInvalidosTest() {
        System.out.println("buscarPorRangoParametrosInvalidos en AspiranteResource");

        Response respuesta = target.path(RESOURCE_NAME_ASPIRANTE)
                .queryParam("first", -1)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(400, respuesta.getStatus());
    }

    @Order(3)
    @Test
    public void crearTest() {
        System.out.println("crear en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response respuesta = target.path(RESOURCE_NAME_ASPIRANTE)
        .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
    }

    @Order(4)
    @Test
    public void crearConIdInvalidoTest() {
        System.out.println("crearConIdInvalido en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setIdAspirante(UUID.randomUUID());
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response respuesta = target.path(RESOURCE_NAME_ASPIRANTE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));

        assertEquals(400, respuesta.getStatus());
    }

    @Order(5)
    @Test
    public void buscarPorIdTest() {
        System.out.println("buscarPorId en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2001, 2, 2));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response crear = target.path(RESOURCE_NAME_ASPIRANTE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        assertEquals(201, crear.getStatus());

        String location = crear.getHeaderString("Location");
        UUID id = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Response buscar = target.path(RESOURCE_NAME_ASPIRANTE)
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, buscar.getStatus());
        Aspirante encontrado = buscar.readEntity(Aspirante.class);
        Assertions.assertNotNull(encontrado);
        assertEquals(id, encontrado.getIdAspirante());
    }

    @Order(6)
    @Test
    public void buscarPorIdNoEncontradoTest() {
        System.out.println("buscarPorIdNoEncontrado en AspiranteResource");

        Response buscar = target.path(RESOURCE_NAME_ASPIRANTE)
                .path(UUID.randomUUID().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(404, buscar.getStatus());
    }

    @Order(7)
    @Test
    public void actualizarTest() {
        System.out.println("actualizar en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2002, 3, 3));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response crear = target.path(RESOURCE_NAME_ASPIRANTE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        assertEquals(201, crear.getStatus());

        String location = crear.getHeaderString("Location");
        UUID id = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Aspirante actualizar = new Aspirante();
        actualizar.setNombres("NOMBRES-ACTUALIZADO");
        actualizar.setApellidos("APELLIDOS-ACTUALIZADO");
        actualizar.setFechaNacimiento(LocalDate.of(2002, 3, 3));
        actualizar.setDocumentoIdentidad("DOC-ACT-ST");
        actualizar.setCorreo("actualizado@test.com");
        actualizar.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response respuesta = target.path(RESOURCE_NAME_ASPIRANTE)
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(actualizar));

        assertEquals(200, respuesta.getStatus());
    }

    @Order(8)
    @Test
    public void eliminarTest() {
        System.out.println("eliminar en AspiranteResource");

        Aspirante nuevo = new Aspirante();
        nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
        nuevo.setApellidos("APELLIDOS-ST");
        nuevo.setFechaNacimiento(LocalDate.of(2003, 4, 4));
        nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
        nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
        nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

        Response crear = target.path(RESOURCE_NAME_ASPIRANTE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(nuevo));
        assertEquals(201, crear.getStatus());

        String location = crear.getHeaderString("Location");
        UUID id = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Response eliminar = target.path(RESOURCE_NAME_ASPIRANTE)
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();

        assertEquals(204, eliminar.getStatus());

        Response buscar = target.path(RESOURCE_NAME_ASPIRANTE)
                .path(id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(404, buscar.getStatus());
    }

        @Order(9)
        @Test
        public void buscarPorApellidosTest() {
                System.out.println("buscarPorApellidos en AspiranteResource");

                String apellidos = "APELLIDOS-ST-BUSCAR-" + UUID.randomUUID().toString().substring(0, 8);
                Aspirante nuevo = new Aspirante();
                nuevo.setNombres("NOMBRES-ST-" + UUID.randomUUID());
                nuevo.setApellidos(apellidos);
                nuevo.setFechaNacimiento(LocalDate.of(2004, 5, 5));
                nuevo.setDocumentoIdentidad("DOC-ST-" + UUID.randomUUID().toString().substring(0, 8));
                nuevo.setCorreo("aspirante" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
                nuevo.setFechaCreacion(OffsetDateTime.now().withNano(0));

                Response crear = target.path(RESOURCE_NAME_ASPIRANTE)
                        .request(MediaType.APPLICATION_JSON)
                                .post(Entity.json(nuevo));
                assertEquals(201, crear.getStatus());

                Response buscar = target.path(RESOURCE_NAME_ASPIRANTE)
                        .path("buscar")
                                .queryParam("apellidos", apellidos)
                                .request(MediaType.APPLICATION_JSON)
                                .get();

                assertEquals(200, buscar.getStatus());
                List<Aspirante> encontrados = buscar.readEntity(new GenericType<List<Aspirante>>() {});
                Assertions.assertNotNull(encontrados);
                Assertions.assertFalse(encontrados.isEmpty());
                Assertions.assertTrue(encontrados.stream().anyMatch(a -> apellidos.equals(a.getApellidos())));
        }

        @Order(10)
        @Test
    public void listarPruebasPorAspirante(){
        crearContextoExamen();

        }
}
