package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaJornadaAulaAspiranteOpcionExamenDAOIT extends AbstractIntengrationDAOTest {

    PruebaJornadaAulaAspiranteOpcionExamenDAO cut; // Class under test

    private static class ContextoCompleto {
        UUID idPrueba;
        UUID idJornada;
        String idAula;
        UUID idAspiranteOpcion;
        UUID idPruebaClave;
    }

    @BeforeEach
    public void setup() {
        cut = new PruebaJornadaAulaAspiranteOpcionExamenDAO();
        cut.em = em;
    }

    private ContextoCompleto crearContextoCompleto(String idAula) {
        ContextoCompleto ctx = new ContextoCompleto();
        UUID uniq = UUID.randomUUID();

        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + uniq);
        em.persist(tipoPrueba);

        Prueba prueba = new Prueba(UUID.randomUUID());
        prueba.setNombre("Prueba " + uniq);
        prueba.setPuntajeMaximo(new BigDecimal("10.00"));
        prueba.setNotaAprobacion(new BigDecimal("6.00"));
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setIdTipoPrueba(tipoPrueba);
        em.persist(prueba);

        Jornada jornada = new Jornada(UUID.randomUUID());
        jornada.setNombre("Jornada " + uniq);
        jornada.setFechaInicio(OffsetDateTime.now());
        jornada.setFechaFin(OffsetDateTime.now().plusDays(1));
        em.persist(jornada);

        PruebaJornada pruebaJornada = new PruebaJornada(prueba, jornada);
        em.persist(pruebaJornada);

        JornadaAula jornadaAula = new JornadaAula(UUID.randomUUID());
        jornadaAula.setIdJornada(jornada);
        jornadaAula.setIdAula(idAula);
        em.persist(jornadaAula);

        Aspirante aspirante = new Aspirante(UUID.randomUUID());
        aspirante.setNombres("Aspirante " + uniq);
        aspirante.setApellidos("Apellido " + uniq);
        aspirante.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        aspirante.setCorreo("asp-" + uniq + "@mail.com");
        aspirante.setFechaCreacion(OffsetDateTime.now());
        em.persist(aspirante);

        AspiranteOpcion aspiranteOpcion = new AspiranteOpcion(UUID.randomUUID());
        aspiranteOpcion.setIdAspirante(aspirante);
        aspiranteOpcion.setIdOpcion("opcion-" + uniq);
        aspiranteOpcion.setFechaCreacion(OffsetDateTime.now());
        em.persist(aspiranteOpcion);

        PruebaJornadaAulaAspiranteOpcion padre = new PruebaJornadaAulaAspiranteOpcion(
                prueba.getIdPrueba(),
                jornada.getIdJornada(),
                idAula,
                aspiranteOpcion.getIdAspiranteOpcion());
        padre.setActivo(true);
        padre.setFecha(OffsetDateTime.now());
        em.persist(padre);

        PruebaClave pruebaClave = new PruebaClave(UUID.randomUUID());
        pruebaClave.setNombreClave("Clave " + uniq);
        pruebaClave.setIdPrueba(prueba);
        em.persist(pruebaClave);

        ctx.idPrueba = prueba.getIdPrueba();
        ctx.idJornada = jornada.getIdJornada();
        ctx.idAula = idAula;
        ctx.idAspiranteOpcion = aspiranteOpcion.getIdAspiranteOpcion();
        ctx.idPruebaClave = pruebaClave.getIdPruebaClave();
        return ctx;
    }

    private PruebaJornadaAulaAspiranteOpcionExamen crearEntidadValidaPersistida() {
        ContextoCompleto ctx = crearContextoCompleto("A-01");

        PruebaJornadaAulaAspiranteOpcionExamen entidad = new PruebaJornadaAulaAspiranteOpcionExamen(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                ctx.idAspiranteOpcion);
        entidad.setIdPruebaClave(ctx.idPruebaClave);
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

        PruebaJornadaAulaAspiranteOpcionExamen nuevo = new PruebaJornadaAulaAspiranteOpcionExamen(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                ctx.idAspiranteOpcion);
        nuevo.setIdPruebaClave(ctx.idPruebaClave);
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
        assertEquals(resultado, 1);
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
                nuevo.getIdPrueba(),
                nuevo.getIdJornada(),
                nuevo.getIdAula(),
                nuevo.getIdAspiranteOpcion());

        PruebaJornadaAulaAspiranteOpcionExamen encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(idBuscado.getIdPrueba(), encontrado.getIdPrueba());
        assertEquals(idBuscado.getIdJornada(), encontrado.getIdJornada());
        assertEquals(idBuscado.getIdAula(), encontrado.getIdAula());
        assertEquals(idBuscado.getIdAspiranteOpcion(), encontrado.getIdAspiranteOpcion());
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

        PruebaJornadaAulaAspiranteOpcionExamen eliminado = new PruebaJornadaAulaAspiranteOpcionExamen(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                ctx.idAspiranteOpcion);
        eliminado.setIdPruebaClave(ctx.idPruebaClave);
        eliminado.setResultado(new BigDecimal("6.00"));

        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionExamenPK idEliminado = new PruebaJornadaAulaAspiranteOpcionExamenPK(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                ctx.idAspiranteOpcion);

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
                eliminado.getIdPrueba(),
                eliminado.getIdJornada(),
                eliminado.getIdAula(),
                eliminado.getIdAspiranteOpcion());

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

        PruebaJornadaAulaAspiranteOpcionExamen registro1 = new PruebaJornadaAulaAspiranteOpcionExamen(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, ctx.idAspiranteOpcion);
        registro1.setIdPruebaClave(ctx.idPruebaClave);
        registro1.setResultado(new BigDecimal("7.00"));
        registro1.setFechaResultado(OffsetDateTime.now().minusMinutes(1));
        cut.crear(registro1);

        PruebaJornadaAulaAspiranteOpcionExamen registro2 = new PruebaJornadaAulaAspiranteOpcionExamen(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, ctx.idAspiranteOpcion);
        registro2.setIdPruebaClave(ctx.idPruebaClave);
        registro2.setResultado(new BigDecimal("8.00"));
        registro2.setFechaResultado(OffsetDateTime.now());
        cut.actualizar(registro2);

        ContextoCompleto otroCtx = crearContextoCompleto("A-11");
        PruebaJornadaAulaAspiranteOpcionExamen otroRegistro = new PruebaJornadaAulaAspiranteOpcionExamen(
                otroCtx.idPrueba, otroCtx.idJornada, otroCtx.idAula, otroCtx.idAspiranteOpcion);
        otroRegistro.setIdPruebaClave(otroCtx.idPruebaClave);
        otroRegistro.setResultado(new BigDecimal("9.00"));
        cut.crear(otroRegistro);

        cut.em.getTransaction().commit();

        List<PruebaJornadaAulaAspiranteOpcionExamen> resultados = cut.buscarPorPadreRango(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, ctx.idAspiranteOpcion, 0, 10);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        System.out.println("Resultado buscarPorPadreRango: " + resultados.size());
    }

    @Order(21)
    @Test
    public void testBuscarPorPadreRangoEmNulo() {
        System.out.println("buscarPorPadreRango em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.buscarPorPadreRango(UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID(), 0, 10);
        });
    }

    @Order(22)
    @Test
    public void testContarPorPadreExitoso() {
        System.out.println("contarPorPadre - exitoso");

        cut.em.getTransaction().begin();
        ContextoCompleto ctx = crearContextoCompleto("A-20");

        PruebaJornadaAulaAspiranteOpcionExamen registro = new PruebaJornadaAulaAspiranteOpcionExamen(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, ctx.idAspiranteOpcion);
        registro.setIdPruebaClave(ctx.idPruebaClave);
        registro.setResultado(new BigDecimal("8.25"));
        cut.crear(registro);

        ContextoCompleto otroCtx = crearContextoCompleto("A-21");
        PruebaJornadaAulaAspiranteOpcionExamen otroRegistro = new PruebaJornadaAulaAspiranteOpcionExamen(
                otroCtx.idPrueba, otroCtx.idJornada, otroCtx.idAula, otroCtx.idAspiranteOpcion);
        otroRegistro.setIdPruebaClave(otroCtx.idPruebaClave);
        otroRegistro.setResultado(new BigDecimal("9.50"));
        cut.crear(otroRegistro);

        cut.em.getTransaction().commit();

        Long resultado = cut.contarPorPadre(ctx.idPrueba, ctx.idJornada, ctx.idAula, ctx.idAspiranteOpcion);
        assertEquals(1L, resultado);
        System.out.println("Resultado contarPorPadre: " + resultado);
    }

    @Order(23)
    @Test
    public void testContarPorPadreEmNulo() {
        System.out.println("contarPorPadre em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.contarPorPadre(UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID());
        });
    }
}
