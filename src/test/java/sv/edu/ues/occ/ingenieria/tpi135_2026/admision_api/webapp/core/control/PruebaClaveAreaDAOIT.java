package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPK;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaClaveAreaDAOIT extends AbstractIntengrationDAOTest {

    PruebaClaveAreaDAO cut; // Class under test

    @BeforeEach
    public void setup() {
        cut = new PruebaClaveAreaDAO();
        cut.em = em;
    }

    private Area crearArea() {
        Area area = new Area(UUID.randomUUID());
        area.setNombre("Area " + UUID.randomUUID());
        em.persist(area);
        return area;
    }

    private PruebaClave crearPruebaClave() {
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + UUID.randomUUID());
        em.persist(tipoPrueba);

        Prueba prueba = new Prueba(UUID.randomUUID());
        prueba.setNombre("Prueba " + UUID.randomUUID());
        prueba.setPuntajeMaximo(new BigDecimal("10.00"));
        prueba.setNotaAprobacion(new BigDecimal("6.00"));
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setIdTipoPrueba(tipoPrueba);
        em.persist(prueba);

        PruebaClave pruebaClave = new PruebaClave(UUID.randomUUID());
        pruebaClave.setNombreClave("Clave " + UUID.randomUUID());
        pruebaClave.setIdPrueba(prueba);
        em.persist(pruebaClave);
        return pruebaClave;
    }

    private PruebaClaveArea crearEntidadValidaPersistida() {
        PruebaClave pruebaClave = crearPruebaClave();
        Area area = crearArea();

        PruebaClaveArea entidad = new PruebaClaveArea(pruebaClave, area);
        entidad.setCantidad(10);
        entidad.setPorcentaje(new BigDecimal("50.00"));
        em.persist(entidad);
        return entidad;
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear prueba_clave_area exitoso");

        cut.em.getTransaction().begin();
        PruebaClave pruebaClave = crearPruebaClave();
        Area area = crearArea();
        PruebaClaveArea nuevo = new PruebaClaveArea(pruebaClave, area);
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear PruebaClaveArea entidad null");
        // Dejamos que falle por la validación de la entidad nula
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crear(null);
        });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear PruebaClaveArea Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.crear(new PruebaClaveArea());
        });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar prueba_clave_area exitoso");
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar prueba_clave_area Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar prueba_clave_area Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId prueba_clave_area exitoso");

        cut.em.getTransaction().begin();
        PruebaClaveArea nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PruebaClaveAreaPK idBuscado = new PruebaClaveAreaPK(
                nuevo.getIdPruebaClave().getIdPruebaClave(),
                nuevo.getIdArea().getIdArea());

        PruebaClaveArea encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(idBuscado.getIdPruebaClave(), encontrado.getIdPruebaClave().getIdPruebaClave());
        assertEquals(idBuscado.getIdArea(), encontrado.getIdArea().getIdArea());
        System.out.println("IdPruebaClave: " + idBuscado.getIdPruebaClave());
        System.out.println("IdArea: " + idBuscado.getIdArea());
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId prueba_clave_area null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorId(null);
        });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId prueba_clave_area em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorId(new PruebaClaveAreaPK(UUID.randomUUID(), UUID.randomUUID()));
        });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar PruebaClaveArea entidad no guardada");

        cut.em.getTransaction().begin();
        PruebaClave pruebaClave = crearPruebaClave();
        Area area = crearArea();
        PruebaClaveArea eliminado = new PruebaClaveArea(pruebaClave, area);
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        PruebaClaveAreaPK idEliminado = new PruebaClaveAreaPK(
                pruebaClave.getIdPruebaClave(),
                area.getIdArea());

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar PruebaClaveArea exitoso");

        cut.em.getTransaction().begin();
        PruebaClaveArea eliminado = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        PruebaClaveAreaPK idEliminado = new PruebaClaveAreaPK(
                eliminado.getIdPruebaClave().getIdPruebaClave(),
                eliminado.getIdArea().getIdArea());

        // eliminamos
        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(12)
    @Test
    public void testEliminarEntidadNula() {
        System.out.println("eliminar prueba_clave_area entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.eliminar(null);
        });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar prueba_clave_area em nulo");
        cut.em = null;
        PruebaClaveArea eliminado = new PruebaClaveArea();
        assertThrows(IllegalStateException.class, () -> {
            cut.eliminar(eliminado);
        });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso() {
        System.out.println("actualizar prueba_clave_area exitoso");

        cut.em.getTransaction().begin();
        PruebaClaveArea nuevo = crearEntidadValidaPersistida();
        cut.em.getTransaction().commit();

        // Modificar la entidad
        nuevo.setCantidad(15);
        cut.em.getTransaction().begin();
        PruebaClaveArea actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getCantidad(), actualizado.getCantidad());
        System.out.println("Cantidad actualizada: " + actualizado.getCantidad());
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar prueba_clave_area entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.actualizar(null);
        });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar prueba_clave_area em nulo");
        // Hacemos la entity manager null
        cut.em = null;
        PruebaClaveArea actualizado = new PruebaClaveArea();

        assertThrows(IllegalStateException.class, () -> {
            cut.actualizar(actualizado);
        });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso() {
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<PruebaClaveArea> resultados = cut.buscarPorRango(0, 50);
        assertNotNull(resultados);
        assertEquals(esperado, resultados.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango prueba_clave_area parametros no validos");
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
        System.out.println("buscarPorRango prueba_clave_area parametros no validos");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorRango(0, 50);
        });
    }

    @Order(20)
    @Test
    public void testBuscarPorPruebaClaveRangoExitoso() {
        System.out.println("buscarPorPruebaClaveRango - exitoso");

        cut.em.getTransaction().begin();
        PruebaClave pruebaClaveObjetivo = crearPruebaClave();

        Area area1 = crearArea();
        PruebaClaveArea relacion1 = new PruebaClaveArea(pruebaClaveObjetivo, area1);
        relacion1.setCantidad(1);
        cut.crear(relacion1);

        Area area2 = crearArea();
        PruebaClaveArea relacion2 = new PruebaClaveArea(pruebaClaveObjetivo, area2);
        relacion2.setCantidad(2);
        cut.crear(relacion2);

        PruebaClave otraPruebaClave = crearPruebaClave();
        Area otraArea = crearArea();
        PruebaClaveArea otraRelacion = new PruebaClaveArea(otraPruebaClave, otraArea);
        otraRelacion.setCantidad(3);
        cut.crear(otraRelacion);
        cut.em.getTransaction().commit();

        List<PruebaClaveArea> resultados = cut.buscarPorPruebaClaveRango(
                pruebaClaveObjetivo.getIdPruebaClave(), 0, 10);

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        System.out.println("Resultado buscarPorPruebaClaveRango: " + resultados.size());
    }

    @Order(21)
    @Test
    public void testBuscarPorPruebaClaveRangoEmNulo() {
        System.out.println("buscarPorPruebaClaveRango prueba_clave_area em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.buscarPorPruebaClaveRango(UUID.randomUUID(), 0, 10);
        });
    }

    @Order(22)
    @Test
    public void testContarPorPruebaClaveExitoso() {
        System.out.println("contarPorPruebaClave - exitoso");

        cut.em.getTransaction().begin();
        PruebaClave pruebaClaveObjetivo = crearPruebaClave();

        Area area1 = crearArea();
        PruebaClaveArea relacion1 = new PruebaClaveArea(pruebaClaveObjetivo, area1);
        cut.crear(relacion1);

        Area area2 = crearArea();
        PruebaClaveArea relacion2 = new PruebaClaveArea(pruebaClaveObjetivo, area2);
        cut.crear(relacion2);

        PruebaClave otraPruebaClave = crearPruebaClave();
        Area otraArea = crearArea();
        PruebaClaveArea otraRelacion = new PruebaClaveArea(otraPruebaClave, otraArea);
        cut.crear(otraRelacion);
        cut.em.getTransaction().commit();

        Long resultado = cut.contarPorPruebaClave(pruebaClaveObjetivo.getIdPruebaClave());
        assertEquals(2L, resultado);
        System.out.println("Resultado contarPorPruebaClave: " + resultado);
    }

    @Order(23)
    @Test
    public void testContarPorPruebaClaveEmNulo() {
        System.out.println("contarPorPruebaClave prueba_clave_area em nulo");
        cut.em = null;
        assertThrows(NullPointerException.class, () -> {
            cut.contarPorPruebaClave(UUID.randomUUID());
        });
    }
}
