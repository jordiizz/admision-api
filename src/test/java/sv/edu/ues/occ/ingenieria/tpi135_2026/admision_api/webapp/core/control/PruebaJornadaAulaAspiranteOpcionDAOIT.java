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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionPK;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaJornadaAulaAspiranteOpcionDAOIT extends AbstractIntengrationDAOTest {

    PruebaJornadaAulaAspiranteOpcionDAO cut; // Class under test

    private static class ContextoPruebaJornadaAula {
        UUID idPrueba;
        UUID idJornada;
        String idAula;
    }

    @BeforeEach
    public void setup() {
        cut = new PruebaJornadaAulaAspiranteOpcionDAO();
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

    private ContextoPruebaJornadaAula crearContextoPruebaJornadaAula(String idAula) {
        ContextoPruebaJornadaAula ctx = new ContextoPruebaJornadaAula();
        UUID uniq = UUID.randomUUID();

        TipoPrueba tipoPrueba = crearTipoPrueba(uniq);
        Prueba prueba = crearPrueba(tipoPrueba, uniq);
        Jornada jornada = crearJornada(uniq);
        crearPruebaJornada(prueba, jornada);
        crearJornadaAula(jornada, idAula);

        ctx.idPrueba = prueba.getIdPrueba();
        ctx.idJornada = jornada.getIdJornada();
        ctx.idAula = idAula;

        return ctx;
    }

    private UUID crearAspiranteOpcion() {
        UUID uniq = UUID.randomUUID();

        Aspirante aspirante = new Aspirante(UUID.randomUUID());
        aspirante.setNombres("Aspirante " + uniq);
        aspirante.setApellidos("Apellidos " + uniq);
        aspirante.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        aspirante.setCorreo("aspirante-" + uniq + "@mail.com");
        aspirante.setFechaCreacion(OffsetDateTime.now());
        em.persist(aspirante);

        AspiranteOpcion aspiranteOpcion = new AspiranteOpcion(UUID.randomUUID());
        aspiranteOpcion.setIdAspirante(aspirante);
        aspiranteOpcion.setIdOpcion("opcion-" + uniq);
        aspiranteOpcion.setFechaCreacion(OffsetDateTime.now());
        em.persist(aspiranteOpcion);

        return aspiranteOpcion.getIdAspiranteOpcion();
    }

    private PruebaJornadaAulaAspiranteOpcion crearEntidadValidaPersistida() {
        ContextoPruebaJornadaAula ctx = crearContextoPruebaJornadaAula("A-01");
        UUID idAspiranteOpcion = crearAspiranteOpcion();

        PruebaJornadaAulaAspiranteOpcion entidad = new PruebaJornadaAulaAspiranteOpcion(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                idAspiranteOpcion);
        entidad.setFecha(OffsetDateTime.now());
        entidad.setActivo(true);
        em.persist(entidad);
        return entidad;
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear prueba_jornada_aula_aspirante_opcion exitoso");

        cut.em.getTransaction().begin();
        ContextoPruebaJornadaAula ctx = crearContextoPruebaJornadaAula("A-01");
        UUID idAspiranteOpcion = crearAspiranteOpcion();

        PruebaJornadaAulaAspiranteOpcion nuevo = new PruebaJornadaAulaAspiranteOpcion(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                idAspiranteOpcion);

        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear PruebaJornadaAulaAspiranteOpcion entidad null");
        // Dejamos que falle por la validación de la entidad nula
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crear(null);
        });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear PruebaJornadaAulaAspiranteOpcion Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.crear(new PruebaJornadaAulaAspiranteOpcion());
        });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar prueba_jornada_aula_aspirante_opcion exitoso");
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar prueba_jornada_aula_aspirante_opcion Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar prueba_jornada_aula_aspirante_opcion Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId prueba_jornada_aula_aspirante_opcion exitoso");

        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcion nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionPK idBuscado = new PruebaJornadaAulaAspiranteOpcionPK(
                nuevo.getIdPrueba(),
                nuevo.getIdJornada(),
                nuevo.getIdAula(),
                nuevo.getIdAspiranteOpcion());

        PruebaJornadaAulaAspiranteOpcion encontrado = cut.buscarPorId(idBuscado);
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
        System.out.println("BuscarPorId prueba_jornada_aula_aspirante_opcion null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorId(null);
        });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId prueba_jornada_aula_aspirante_opcion em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorId(new PruebaJornadaAulaAspiranteOpcionPK(
                    UUID.randomUUID(), UUID.randomUUID(), "A-01", UUID.randomUUID()));
        });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar PruebaJornadaAulaAspiranteOpcion entidad no guardada");

        cut.em.getTransaction().begin();
        ContextoPruebaJornadaAula ctx = crearContextoPruebaJornadaAula("A-02");
        UUID idAspiranteOpcion = crearAspiranteOpcion();

        PruebaJornadaAulaAspiranteOpcion eliminado = new PruebaJornadaAulaAspiranteOpcion(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                idAspiranteOpcion);
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionPK idEliminado = new PruebaJornadaAulaAspiranteOpcionPK(
                ctx.idPrueba,
                ctx.idJornada,
                ctx.idAula,
                idAspiranteOpcion);

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar PruebaJornadaAulaAspiranteOpcion exitoso");

        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcion eliminado = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PruebaJornadaAulaAspiranteOpcionPK idEliminado = new PruebaJornadaAulaAspiranteOpcionPK(
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
        System.out.println("eliminar prueba_jornada_aula_aspirante_opcion entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.eliminar(null);
        });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar prueba_jornada_aula_aspirante_opcion em nulo");
        cut.em = null;
        PruebaJornadaAulaAspiranteOpcion eliminado = new PruebaJornadaAulaAspiranteOpcion();
        assertThrows(IllegalStateException.class, () -> {
            cut.eliminar(eliminado);
        });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso() {
        System.out.println("actualizar prueba_jornada_aula_aspirante_opcion exitoso");

        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcion nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        // Modificar la entidad
        nuevo.setActivo(false);
        cut.em.getTransaction().begin();
        PruebaJornadaAulaAspiranteOpcion actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getActivo(), actualizado.getActivo());
        System.out.println("Activo actualizado: " + actualizado.getActivo());
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar prueba_jornada_aula_aspirante_opcion entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.actualizar(null);
        });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar prueba_jornada_aula_aspirante_opcion em nulo");
        // Hacemos la entity manager null
        cut.em = null;
        PruebaJornadaAulaAspiranteOpcion actualizado = new PruebaJornadaAulaAspiranteOpcion();

        assertThrows(IllegalStateException.class, () -> {
            cut.actualizar(actualizado);
        });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso() {
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<PruebaJornadaAulaAspiranteOpcion> resultados = cut.buscarPorRango(0, 50);
        assertNotNull(resultados);
        assertEquals(esperado, resultados.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango prueba_jornada_aula_aspirante_opcion parametros no validos");
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
        System.out.println("buscarPorRango prueba_jornada_aula_aspirante_opcion parametros no validos");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorRango(0, 50);
        });
    }

    @Order(20)
    @Test
    public void testBuscarPorPruebaJornadaYJornadaAulaRangoExitoso() {
        System.out.println("buscarPorPruebaJornadaYJornadaAulaRango - exitoso");

        cut.em.getTransaction().begin();
        // Contexto objetivo para el filtro del método
        ContextoPruebaJornadaAula ctx = crearContextoPruebaJornadaAula("A-10");

        // Dos registros que sí deben aparecer en el resultado filtrado
        UUID idAspiranteOpcion1 = crearAspiranteOpcion();
        PruebaJornadaAulaAspiranteOpcion relacion1 = new PruebaJornadaAulaAspiranteOpcion(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, idAspiranteOpcion1);
        cut.crear(relacion1);

        UUID idAspiranteOpcion2 = crearAspiranteOpcion();
        PruebaJornadaAulaAspiranteOpcion relacion2 = new PruebaJornadaAulaAspiranteOpcion(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, idAspiranteOpcion2);
        cut.crear(relacion2);

        // Registro de otro contexto para comprobar que no se incluya en el filtro
        ContextoPruebaJornadaAula otroCtx = crearContextoPruebaJornadaAula("A-11");
        UUID idAspiranteOpcion3 = crearAspiranteOpcion();
        PruebaJornadaAulaAspiranteOpcion relacion3 = new PruebaJornadaAulaAspiranteOpcion(
                otroCtx.idPrueba, otroCtx.idJornada, otroCtx.idAula, idAspiranteOpcion3);
        cut.crear(relacion3);

        cut.em.getTransaction().commit();

        List<PruebaJornadaAulaAspiranteOpcion> resultados = cut.buscarPorPruebaJornadaYJornadaAulaRango(
                ctx.idPrueba, ctx.idJornada, ctx.idAula, 0, 10);

        assertNotNull(resultados);
        // Deben regresar solo los 2 del contexto objetivo
        assertEquals(2, resultados.size());
        System.out.println("Resultado buscarPorPruebaJornadaYJornadaAulaRango: " + resultados.size());
    }

    @Order(21)
    @Test
    public void testBuscarPorPruebaJornadaYJornadaAulaRangoEmNulo() {
        System.out.println("buscarPorPruebaJornadaYJornadaAulaRango em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.buscarPorPruebaJornadaYJornadaAulaRango(UUID.randomUUID(), UUID.randomUUID(), "A-01", 0, 10);
        });
    }

    @Order(22)
    @Test
    public void testContarPorPruebaJornadaYJornadaAulaExitoso() {
        System.out.println("contarPorPruebaJornadaYJornadaAula - exitoso");

        cut.em.getTransaction().begin();
        ContextoPruebaJornadaAula ctx = crearContextoPruebaJornadaAula("A-20");

        UUID idAspiranteOpcion1 = crearAspiranteOpcion();
        cut.crear(new PruebaJornadaAulaAspiranteOpcion(ctx.idPrueba, ctx.idJornada, ctx.idAula, idAspiranteOpcion1));

        UUID idAspiranteOpcion2 = crearAspiranteOpcion();
        cut.crear(new PruebaJornadaAulaAspiranteOpcion(ctx.idPrueba, ctx.idJornada, ctx.idAula, idAspiranteOpcion2));

        ContextoPruebaJornadaAula otroCtx = crearContextoPruebaJornadaAula("A-21");
        UUID idAspiranteOpcion3 = crearAspiranteOpcion();
        cut.crear(new PruebaJornadaAulaAspiranteOpcion(otroCtx.idPrueba, otroCtx.idJornada, otroCtx.idAula, idAspiranteOpcion3));

        cut.em.getTransaction().commit();

        Long resultado = cut.contarPorPruebaJornadaYJornadaAula(ctx.idPrueba, ctx.idJornada, ctx.idAula);
        assertEquals(2L, resultado);
        System.out.println("Resultado contarPorPruebaJornadaYJornadaAula: " + resultado);
    }

    @Order(23)
    @Test
    public void testContarPorPruebaJornadaYJornadaAulaEmNulo() {
        System.out.println("contarPorPruebaJornadaYJornadaAula em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.contarPorPruebaJornadaYJornadaAula(UUID.randomUUID(), UUID.randomUUID(), "A-01");
        });
    }
}
