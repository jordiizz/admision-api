package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import jakarta.persistence.EntityManager;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Aspirante;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamenPK;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaJornadaAulaAspiranteOpcionExamenDAOIT extends AbstractIntengrationDAOTest {

    PruebaJornadaAulaAspiranteOpcionExamenDAO cut; // Class under test

    private static class ContextoCompleto {
        Prueba prueba;
        Jornada jornada;
        String idAula;
        AspiranteOpcion aspiranteOpcion;
        PruebaClave pruebaClave;
    }

    @BeforeEach
    public void setup() {
        cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();
        cut.em = em;
    }

    private TipoPrueba crearTipoPrueba(UUID uniq) {
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + uniq);
        em.persist(tipoPrueba);
        return tipoPrueba;
    }

    private Prueba crearPrueba(TipoPrueba tipoPrueba, UUID uniq) {
        Prueba prueba = new Prueba(UUID.randomUUID());
        prueba.setNombre("Prueba " + uniq);
        prueba.setPuntajeMaximo(new BigDecimal("10.00"));
        prueba.setNotaAprobacion(new BigDecimal("6.00"));
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setIdTipoPrueba(tipoPrueba);
        em.persist(prueba);
        return prueba;
    }

    private Jornada crearJornada(UUID uniq) {
        Jornada jornada = new Jornada(UUID.randomUUID());
        jornada.setNombre("Jornada " + uniq);
        jornada.setFechaInicio(OffsetDateTime.now());
        jornada.setFechaFin(OffsetDateTime.now().plusDays(1));
        em.persist(jornada);
        return jornada;
    }

    private void crearPruebaJornada(Prueba prueba, Jornada jornada) {
        PruebaJornada pruebaJornada = new PruebaJornada(prueba, jornada);
        em.persist(pruebaJornada);
    }

    private void crearJornadaAula(Jornada jornada, String idAula) {
        JornadaAula jornadaAula = new JornadaAula(UUID.randomUUID());
        jornadaAula.setIdJornada(jornada);
        jornadaAula.setIdAula(idAula);
        em.persist(jornadaAula);
    }

    private Aspirante crearAspirante(UUID uniq) {
        Aspirante aspirante = new Aspirante(UUID.randomUUID());
        aspirante.setNombres("Aspirante " + uniq);
        aspirante.setApellidos("Apellido " + uniq);
        aspirante.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        aspirante.setCorreo("asp-" + uniq + "@mail.com");
        aspirante.setFechaCreacion(OffsetDateTime.now());
        em.persist(aspirante);
        return aspirante;
    }

    private AspiranteOpcion crearAspiranteOpcion(Aspirante aspirante, UUID uniq) {
        AspiranteOpcion aspiranteOpcion = new AspiranteOpcion(UUID.randomUUID());
        aspiranteOpcion.setIdAspirante(aspirante);
        aspiranteOpcion.setIdOpcion("opcion-" + uniq);
        aspiranteOpcion.setFechaCreacion(OffsetDateTime.now());
        em.persist(aspiranteOpcion);
        return aspiranteOpcion;
    }

    private void crearPadre(Prueba prueba, Jornada jornada, String idAula, AspiranteOpcion aspiranteOpcion) {
        PruebaJornadaAulaAspiranteOpcion padre = new PruebaJornadaAulaAspiranteOpcion(
            prueba,
            jornada,
            idAula,
            aspiranteOpcion);
        padre.setActivo(true);
        padre.setFecha(OffsetDateTime.now());
        em.persist(padre);
    }

    private PruebaClave crearPruebaClave(Prueba prueba, UUID uniq) {
        PruebaClave pruebaClave = new PruebaClave(UUID.randomUUID());
        pruebaClave.setNombreClave("Clave " + uniq);
        pruebaClave.setIdPrueba(prueba);
        em.persist(pruebaClave);
        return pruebaClave;
    }

    private ContextoCompleto crearContextoCompleto(String idAula) {
        ContextoCompleto ctx = new ContextoCompleto();
        UUID uniq = UUID.randomUUID();

        TipoPrueba tipoPrueba = crearTipoPrueba(uniq);
        Prueba prueba = crearPrueba(tipoPrueba, uniq);
        Jornada jornada = crearJornada(uniq);
        crearPruebaJornada(prueba, jornada);
        crearJornadaAula(jornada, idAula);
        Aspirante aspirante = crearAspirante(uniq);
        AspiranteOpcion aspiranteOpcion = crearAspiranteOpcion(aspirante, uniq);
        crearPadre(prueba, jornada, idAula, aspiranteOpcion);
        PruebaClave pruebaClave = crearPruebaClave(prueba, uniq);

        ctx.prueba = prueba;
        ctx.jornada = jornada;
        ctx.idAula = idAula;
        ctx.aspiranteOpcion = aspiranteOpcion;
        ctx.pruebaClave = pruebaClave;
        return ctx;
    }

    private PruebaJornadaAulaAspiranteOpcionExamen crearExamen(Prueba prueba, Jornada jornada, String idAula, AspiranteOpcion aspiranteOpcion, PruebaClave pruebaClave) {
        PruebaJornadaAulaAspiranteOpcionExamen examen = new PruebaJornadaAulaAspiranteOpcionExamen(
                prueba,
                jornada,
                idAula,
                aspiranteOpcion);
        examen.setIdPruebaClave(pruebaClave);
        return examen;
    }

    private PruebaJornadaAulaAspiranteOpcionExamen crearEntidadValidaPersistida() {
        ContextoCompleto ctx = crearContextoCompleto("A-01");

        PruebaJornadaAulaAspiranteOpcionExamen entidad = crearExamen(
                ctx.prueba,
                ctx.jornada,
                ctx.idAula,
                ctx.aspiranteOpcion,
                ctx.pruebaClave);
        entidad.setResultado(new BigDecimal("8.50"));
        entidad.setFechaResultado(OffsetDateTime.now());
        em.persist(entidad);
        return entidad;
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear prueba_jornada_aula_aspirante_opcion_examen exitoso");

        cut.em.getTransaction().begin();
        ContextoCompleto ctx = crearContextoCompleto("A-01");

        PruebaJornadaAulaAspiranteOpcionExamen nuevo = crearExamen(
            ctx.prueba,
            ctx.jornada,
            ctx.idAula,
            ctx.aspiranteOpcion,
            ctx.pruebaClave);
        nuevo.setResultado(new BigDecimal("7.75"));

        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear PruebaJornadaAulaAspiranteOpcionExamen entidad null");
        // Dejamos que falle por la validación de la entidad nula
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crear(null);
        });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear PruebaJornadaAulaAspiranteOpcionExamen Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.crear(new PruebaJornadaAulaAspiranteOpcionExamen());
        });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar prueba_jornada_aula_aspirante_opcion_examen exitoso");
        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar prueba_jornada_aula_aspirante_opcion_examen Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar prueba_jornada_aula_aspirante_opcion_examen Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId prueba_jornada_aula_aspirante_opcion_examen exitoso");

        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcionExamen nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionExamenPK idBuscado = new PruebaJornadaAulaAspiranteOpcionExamenPK(
            nuevo.getIdPrueba().getIdPrueba(),
            nuevo.getIdJornada().getIdJornada(),
                nuevo.getIdAula(),
            nuevo.getIdAspiranteOpcion().getIdAspiranteOpcion());

        PruebaJornadaAulaAspiranteOpcionExamen encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(idBuscado.getIdPrueba(), encontrado.getIdPrueba().getIdPrueba());
        assertEquals(idBuscado.getIdJornada(), encontrado.getIdJornada().getIdJornada());
        assertEquals(idBuscado.getIdAula(), encontrado.getIdAula());
        assertEquals(idBuscado.getIdAspiranteOpcion(), encontrado.getIdAspiranteOpcion().getIdAspiranteOpcion());
        System.out.println("IdAspiranteOpcion: " + idBuscado.getIdAspiranteOpcion());
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId prueba_jornada_aula_aspirante_opcion_examen null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorId(null);
        });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId prueba_jornada_aula_aspirante_opcion_examen em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorId(new PruebaJornadaAulaAspiranteOpcionExamenPK(
                    UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID()));
        });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar PruebaJornadaAulaAspiranteOpcionExamen entidad no guardada");

        cut.em.getTransaction().begin();
        ContextoCompleto ctx = crearContextoCompleto("A-02");

        PruebaJornadaAulaAspiranteOpcionExamen eliminado = crearExamen(
            ctx.prueba,
            ctx.jornada,
            ctx.idAula,
            ctx.aspiranteOpcion,
            ctx.pruebaClave);
        eliminado.setResultado(new BigDecimal("6.00"));

        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionExamenPK idEliminado = new PruebaJornadaAulaAspiranteOpcionExamenPK(
            ctx.prueba.getIdPrueba(),
            ctx.jornada.getIdJornada(),
                ctx.idAula,
            ctx.aspiranteOpcion.getIdAspiranteOpcion());

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar PruebaJornadaAulaAspiranteOpcionExamen exitoso");

        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcionExamen eliminado = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionExamenPK idEliminado = new PruebaJornadaAulaAspiranteOpcionExamenPK(
            eliminado.getIdPrueba().getIdPrueba(),
            eliminado.getIdJornada().getIdJornada(),
                eliminado.getIdAula(),
            eliminado.getIdAspiranteOpcion().getIdAspiranteOpcion());

        // eliminamos
        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(12)
    @Test
    public void testEliminarEntidadNula() {
        System.out.println("eliminar prueba_jornada_aula_aspirante_opcion_examen entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.eliminar(null);
        });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar prueba_jornada_aula_aspirante_opcion_examen em nulo");
        cut.em = null;
        PruebaJornadaAulaAspiranteOpcionExamen eliminado = new PruebaJornadaAulaAspiranteOpcionExamen();
        assertThrows(IllegalStateException.class, () -> {
            cut.eliminar(eliminado);
        });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso() {
        System.out.println("actualizar prueba_jornada_aula_aspirante_opcion_examen exitoso");

        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcionExamen nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        // Modificar la entidad
        nuevo.setResultado(new BigDecimal("9.25"));
        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcionExamen actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getResultado(), actualizado.getResultado());
        System.out.println("Resultado actualizado: " + actualizado.getResultado());
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar prueba_jornada_aula_aspirante_opcion_examen entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.actualizar(null);
        });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar prueba_jornada_aula_aspirante_opcion_examen em nulo");
        // Hacemos la entity manager null
        cut.em = null;
        PruebaJornadaAulaAspiranteOpcionExamen actualizado = new PruebaJornadaAulaAspiranteOpcionExamen();

        assertThrows(IllegalStateException.class, () -> {
            cut.actualizar(actualizado);
        });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso() {
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<PruebaJornadaAulaAspiranteOpcionExamen> resultados = cut.buscarPorRango(0, 50);
        assertNotNull(resultados);
        assertEquals(esperado, resultados.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango prueba_jornada_aula_aspirante_opcion_examen parametros no validos");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorRango(0, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorRango(-1, 50);
        });
    }

    @Order(19)
    @Test
    public void testBuscarPorRangoEmNull() {
        System.out.println("buscarPorRango prueba_jornada_aula_aspirante_opcion_examen parametros no validos");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorRango(0, 50);
        });
    }

    @Order(20)
    @Test
    public void testBuscarPorPadreRangoExitoso() {
        System.out.println("buscarPorPadreRango - exitoso");

        cut.em.getTransaction().begin();
        ContextoCompleto ctx = crearContextoCompleto("A-10");

        PruebaJornadaAulaAspiranteOpcionExamen registro1 = crearExamen(
            ctx.prueba, ctx.jornada, ctx.idAula, ctx.aspiranteOpcion, ctx.pruebaClave);
        registro1.setResultado(new BigDecimal("7.00"));
        registro1.setFechaResultado(OffsetDateTime.now().minusMinutes(1));
        cut.crear(registro1);

        PruebaJornadaAulaAspiranteOpcionExamen registro2 = crearExamen(
            ctx.prueba, ctx.jornada, ctx.idAula, ctx.aspiranteOpcion, ctx.pruebaClave);
        registro2.setResultado(new BigDecimal("8.00"));
        registro2.setFechaResultado(OffsetDateTime.now());
        cut.actualizar(registro2);

        ContextoCompleto otroCtx = crearContextoCompleto("A-11");
        PruebaJornadaAulaAspiranteOpcionExamen otroRegistro = crearExamen(
            otroCtx.prueba, otroCtx.jornada, otroCtx.idAula, otroCtx.aspiranteOpcion, otroCtx.pruebaClave);
        otroRegistro.setResultado(new BigDecimal("9.00"));
        cut.crear(otroRegistro);

        cut.em.getTransaction().commit();

        List<PruebaJornadaAulaAspiranteOpcionExamen> resultados = cut.buscarPorPadreRango(
            ctx.prueba.getIdPrueba(),
            ctx.jornada.getIdJornada(),
            ctx.idAula,
            ctx.aspiranteOpcion.getIdAspiranteOpcion(),
            0,
            10);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        System.out.println("Resultado buscarPorPadreRango: " + resultados.size());
    }

    @Order(21)
    @Test
    public void testBuscarPorPadreRangoEmNulo() {
        System.out.println("buscarPorPadreRango em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorPadreRango(UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID(), 0, 10);
        });
    }

    @Order(22)
    @Test
    public void testContarPorPadreExitoso() {
        System.out.println("contarPorPadre - exitoso");

        cut.em.getTransaction().begin();
        ContextoCompleto ctx = crearContextoCompleto("A-20");

        PruebaJornadaAulaAspiranteOpcionExamen registro = crearExamen(
            ctx.prueba, ctx.jornada, ctx.idAula, ctx.aspiranteOpcion, ctx.pruebaClave);
        registro.setResultado(new BigDecimal("8.25"));
        cut.crear(registro);

        ContextoCompleto otroCtx = crearContextoCompleto("A-21");
        PruebaJornadaAulaAspiranteOpcionExamen otroRegistro = crearExamen(
            otroCtx.prueba, otroCtx.jornada, otroCtx.idAula, otroCtx.aspiranteOpcion, otroCtx.pruebaClave);
        otroRegistro.setResultado(new BigDecimal("9.50"));
        cut.crear(otroRegistro);

        cut.em.getTransaction().commit();

        Long resultado = cut.contarPorPadre(
            ctx.prueba.getIdPrueba(),
            ctx.jornada.getIdJornada(),
            ctx.idAula,
            ctx.aspiranteOpcion.getIdAspiranteOpcion());
        assertEquals(1L, resultado);
        System.out.println("Resultado contarPorPadre: " + resultado);
    }

    @Order(23)
    @Test
    public void testContarPorPadreEmNulo() {
        System.out.println("contarPorPadre em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.contarPorPadre(UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID());
        });
    }

    @Order(24)
    @Test
    public void testBuscarPorPadreRangoParametrosInvalidos() {
        System.out.println("buscarPorPadreRango parametros no validos");

        IllegalArgumentException exNulo = assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(null, UUID.randomUUID(), "A-01", UUID.randomUUID(), 0, 10));
        assertEquals("Parámetros inválidos", exNulo.getMessage());

        IllegalArgumentException exJornadaNula = assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(UUID.randomUUID(), null, "A-01", UUID.randomUUID(), 0, 10));
        assertEquals("Parámetros inválidos", exJornadaNula.getMessage());

        IllegalArgumentException exAulaNula = assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(UUID.randomUUID(), UUID.randomUUID(), null, UUID.randomUUID(), 0, 10));
        assertEquals("Parámetros inválidos", exAulaNula.getMessage());

        IllegalArgumentException exAspiranteNulo = assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(UUID.randomUUID(), UUID.randomUUID(), "A-01", null, 0, 10));
        assertEquals("Parámetros inválidos", exAspiranteNulo.getMessage());

        IllegalArgumentException exFirst = assertThrows(IllegalArgumentException.class,
            () -> cut.buscarPorPadreRango(UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID(), -1, 10));
        assertEquals("Parámetros inválidos", exFirst.getMessage());

        IllegalArgumentException exRango = assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorPadreRango(UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID(), 0, 0));
        assertEquals("Parámetros inválidos", exRango.getMessage());
    }

    @Order(25)
    @Test
    public void testContarPorPadreParametrosInvalidos() {
        System.out.println("contarPorPadre parametros no validos");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cut.contarPorPadre(UUID.randomUUID(), UUID.randomUUID(), null, UUID.randomUUID());
        });
        assertEquals("Parámetros inválidos", ex.getMessage());

        IllegalArgumentException exPruebaNula = assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPadre(null, UUID.randomUUID(), "A-01", UUID.randomUUID()));
        assertEquals("Parámetros inválidos", exPruebaNula.getMessage());

        IllegalArgumentException exJornadaNula = assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPadre(UUID.randomUUID(), null, "A-01", UUID.randomUUID()));
        assertEquals("Parámetros inválidos", exJornadaNula.getMessage());

        IllegalArgumentException exAspiranteNulo = assertThrows(IllegalArgumentException.class,
            () -> cut.contarPorPadre(UUID.randomUUID(), UUID.randomUUID(), "A-01", null));
        assertEquals("Parámetros inválidos", exAspiranteNulo.getMessage());
    }

    @Order(26)
    @Test
    public void testObtenerResultadoExamenPorAspiranteYPruebaExitoso() {
        System.out.println("obtenerResultadoExamenPorAspiranteYPrueba - exitoso");

        cut.em.getTransaction().begin();
        ContextoCompleto ctx = crearContextoCompleto("A-30");

        PruebaJornadaAulaAspiranteOpcionExamen examen = crearExamen(
            ctx.prueba,
            ctx.jornada,
            ctx.idAula,
            ctx.aspiranteOpcion,
            ctx.pruebaClave);
        examen.setResultado(new BigDecimal("8.50"));
        examen.setFechaResultado(OffsetDateTime.now());
        cut.crear(examen);
        cut.em.getTransaction().commit();

        // Act
        PruebaJornadaAulaAspiranteOpcionExamen resultado = cut.obtenerResultadoExamenPorAspiranteYPrueba(
            ctx.aspiranteOpcion.getIdAspirante().getIdAspirante(),
            ctx.prueba.getIdPrueba());

        // Assert
        assertNotNull(resultado);
        assertEquals(examen.getIdPrueba().getIdPrueba(), resultado.getIdPrueba().getIdPrueba());
        assertEquals(examen.getResultado(), resultado.getResultado());
        System.out.println("Resultado encontrado: " + resultado.getResultado());
    }

    @Order(27)
    @Test
    public void testObtenerResultadoExamenPorAspiranteYPruebaNoEncontrado() {
        System.out.println("obtenerResultadoExamenPorAspiranteYPrueba - no encontrado");

        UUID idAspiranteNoExistente = UUID.randomUUID();
        UUID idPruebaNoExistente = UUID.randomUUID();

        // Act
        PruebaJornadaAulaAspiranteOpcionExamen resultado = cut.obtenerResultadoExamenPorAspiranteYPrueba(
            idAspiranteNoExistente,
            idPruebaNoExistente);

        // Assert
        assertNull(resultado);
        System.out.println("Resultado esperado: null (entidad no encontrada)");
    }

    @Order(28)
    @Test
    public void testObtenerResultadoExamenPorAspiranteYPruebaIdAspiranteNulo() {
        System.out.println("obtenerResultadoExamenPorAspiranteYPrueba - idAspirante nulo");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerResultadoExamenPorAspiranteYPrueba(null, UUID.randomUUID());
        });

        assertEquals("idAspirante y idPrueba no pueden ser nulos", ex.getMessage());
    }

    @Order(29)
    @Test
    public void testObtenerResultadoExamenPorAspiranteYPruebaIdPruebaNulo() {
        System.out.println("obtenerResultadoExamenPorAspiranteYPrueba - idPrueba nulo");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerResultadoExamenPorAspiranteYPrueba(UUID.randomUUID(), null);
        });

        assertEquals("idAspirante y idPrueba no pueden ser nulos", ex.getMessage());
    }

    @Order(30)
    @Test
    public void testObtenerResultadoExamenPorAspiranteYPruebaAmbosParametrosNulos() {
        System.out.println("obtenerResultadoExamenPorAspiranteYPrueba - ambos parámetros nulos");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerResultadoExamenPorAspiranteYPrueba(null, null);
        });

        assertEquals("idAspirante y idPrueba no pueden ser nulos", ex.getMessage());
    }

    @Order(31)
    @Test
    public void testObtenerResultadoExamenPorAspiranteYPruebaEmNulo() {
        System.out.println("obtenerResultadoExamenPorAspiranteYPrueba - em nulo");

        cut.em = null;

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            cut.obtenerResultadoExamenPorAspiranteYPrueba(UUID.randomUUID(), UUID.randomUUID());
        });

        assertEquals("El repositorio es nulo", ex.getMessage());
    }
}
