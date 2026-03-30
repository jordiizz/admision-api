package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaAreaPK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreguntaAreaDAOIT extends AbstractIntengrationDAOTest {

    PreguntaAreaDAO cut; // Class under test

    private static class ContextoPreguntaArea {
        Pregunta pregunta;
        Area area;
        UUID idPregunta;
        UUID idArea;
    }

    @BeforeEach
    public void setup() {
        cut = new PreguntaAreaDAO();
        cut.em = em;
    }

    private Pregunta crearPregunta() {
        Pregunta pregunta = new Pregunta(UUID.randomUUID());
        pregunta.setValor("Pregunta " + UUID.randomUUID());
        em.persist(pregunta);
        return pregunta;
    }

    private Area crearArea() {
        Area area = new Area(UUID.randomUUID());
        area.setNombre("Area " + UUID.randomUUID());
        em.persist(area);
        return area;
    }

    private ContextoPreguntaArea crearContextoPreguntaArea() {
        // Contexto minimo para relacion pregunta_area (ambas tablas padre existentes).
        ContextoPreguntaArea ctx = new ContextoPreguntaArea();
        Pregunta pregunta = crearPregunta();
        Area area = crearArea();

        ctx.pregunta = pregunta;
        ctx.area = area;
        ctx.idPregunta = pregunta.getIdPregunta();
        ctx.idArea = area.getIdArea();
        return ctx;
    }

    private PreguntaArea crearEntidadValidaPersistida() {
        ContextoPreguntaArea ctx = crearContextoPreguntaArea();

        PreguntaArea entidad = new PreguntaArea(
            ctx.pregunta,
            ctx.area);
        em.persist(entidad);
        return entidad;
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear pregunta_area exitoso");

        cut.em.getTransaction().begin();
        ContextoPreguntaArea ctx = crearContextoPreguntaArea();

        PreguntaArea nuevo = new PreguntaArea(
            ctx.pregunta,
            ctx.area);

        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear PreguntaArea entidad null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crear(null);
        });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear PreguntaArea Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.crear(new PreguntaArea());
        });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar pregunta_area exitoso");
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar pregunta_area Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar pregunta_area Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId pregunta_area exitoso");

        cut.em.getTransaction().begin();
        PreguntaArea nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PreguntaAreaPK idBuscado = new PreguntaAreaPK(
                nuevo.getIdPregunta().getIdPregunta(),
                nuevo.getIdArea().getIdArea());

        PreguntaArea encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(idBuscado.getIdPregunta(), encontrado.getIdPregunta().getIdPregunta());
        assertEquals(idBuscado.getIdArea(), encontrado.getIdArea().getIdArea());
        System.out.println("IdPregunta: " + idBuscado.getIdPregunta());
        System.out.println("IdArea: " + idBuscado.getIdArea());
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId pregunta_area null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorId(null);
        });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId pregunta_area em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorId(new PreguntaAreaPK(UUID.randomUUID(), UUID.randomUUID()));
        });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar PreguntaArea entidad no guardada");

        cut.em.getTransaction().begin();
        ContextoPreguntaArea ctx = crearContextoPreguntaArea();

        PreguntaArea eliminado = new PreguntaArea(
            ctx.pregunta,
            ctx.area);
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        PreguntaAreaPK idEliminado = new PreguntaAreaPK(ctx.idPregunta, ctx.idArea);
        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar PreguntaArea exitoso");

        cut.em.getTransaction().begin();
        PreguntaArea eliminado = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PreguntaAreaPK idEliminado = new PreguntaAreaPK(
                eliminado.getIdPregunta().getIdPregunta(),
                eliminado.getIdArea().getIdArea());

        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(12)
    @Test
    public void testEliminarEntidadNula() {
        System.out.println("eliminar pregunta_area entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.eliminar(null);
        });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar pregunta_area em nulo");
        cut.em = null;
        PreguntaArea eliminado = new PreguntaArea();
        assertThrows(IllegalStateException.class, () -> {
            cut.eliminar(eliminado);
        });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso() {
        System.out.println("actualizar pregunta_area exitoso");

        cut.em.getTransaction().begin();
        PreguntaArea nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        cut.em.getTransaction().begin();
        PreguntaArea actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getIdPregunta().getIdPregunta(), actualizado.getIdPregunta().getIdPregunta());
        assertEquals(nuevo.getIdArea().getIdArea(), actualizado.getIdArea().getIdArea());
        System.out.println("Relacion actualizada: " + actualizado);
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar pregunta_area entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.actualizar(null);
        });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar pregunta_area em nulo");
        cut.em = null;
        PreguntaArea actualizado = new PreguntaArea();

        assertThrows(IllegalStateException.class, () -> {
            cut.actualizar(actualizado);
        });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso() {
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<PreguntaArea> resultados = cut.buscarPorRango(0, 50);
        assertNotNull(resultados);
        assertEquals(esperado, resultados.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango pregunta_area parametros no validos");
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
        System.out.println("buscarPorRango pregunta_area em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorRango(0, 50);
        });
    }

    @Order(20)
    @Test
    public void testBuscarPorPreguntaRangoExitoso() {
        System.out.println("buscarPorPreguntaRango - exitoso");

        cut.em.getTransaction().begin();
        Pregunta preguntaObjetivo = crearPregunta();

        Area area1 = crearArea();
        PreguntaArea relacion1 = new PreguntaArea(preguntaObjetivo, area1);
        cut.crear(relacion1);

        Area area2 = crearArea();
        PreguntaArea relacion2 = new PreguntaArea(preguntaObjetivo, area2);
        cut.crear(relacion2);

        // Otro contexto para validar que el filtro solo traiga la pregunta objetivo.
        Pregunta otraPregunta = crearPregunta();
        Area otraArea = crearArea();
        PreguntaArea otraRelacion = new PreguntaArea(otraPregunta, otraArea);
        cut.crear(otraRelacion);

        cut.em.getTransaction().commit();

        List<PreguntaArea> resultados = cut.buscarPorPreguntaRango(
                preguntaObjetivo.getIdPregunta(), 0, 10);

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        System.out.println("Resultado buscarPorPreguntaRango: " + resultados.size());
    }

    @Order(21)
    @Test
    public void testBuscarPorPreguntaRangoEmNulo() {
        System.out.println("buscarPorPreguntaRango pregunta_area em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.buscarPorPreguntaRango(UUID.randomUUID(), 0, 10);
        });
    }

    @Order(22)
    @Test
    public void testContarPorPreguntaExitoso() {
        System.out.println("contarPorPregunta - exitoso");

        cut.em.getTransaction().begin();
        Pregunta preguntaObjetivo = crearPregunta();

        Area area1 = crearArea();
        PreguntaArea relacion1 = new PreguntaArea(preguntaObjetivo, area1);
        cut.crear(relacion1);

        Area area2 = crearArea();
        PreguntaArea relacion2 = new PreguntaArea(preguntaObjetivo, area2);
        cut.crear(relacion2);

        // Otra pregunta para comprobar que el conteo es por pregunta y no global.
        Pregunta otraPregunta = crearPregunta();
        Area otraArea = crearArea();
        PreguntaArea otraRelacion = new PreguntaArea(otraPregunta, otraArea);
        cut.crear(otraRelacion);

        cut.em.getTransaction().commit();

        Long resultado = cut.contarPorPregunta(preguntaObjetivo.getIdPregunta());
        assertEquals(2L, resultado);
        System.out.println("Resultado contarPorPregunta: " + resultado);
    }

    @Order(23)
    @Test
    public void testContarPorPreguntaEmNulo() {
        System.out.println("contarPorPregunta pregunta_area em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.contarPorPregunta(UUID.randomUUID());
        });
    }
}
