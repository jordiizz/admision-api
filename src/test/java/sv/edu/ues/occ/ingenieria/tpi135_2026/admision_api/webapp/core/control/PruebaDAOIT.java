package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.math.BigDecimal;
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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaDAOIT extends AbstractIntengrationDAOTest {

    PruebaDAO cut; // Class under test

    @BeforeEach
    public void setup() {
        // Reiniciamos el DAO y su EntityManager antes de cada escenario.
        cut = new PruebaDAO();
        cut.em = em;
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear prueba exitoso");

        cut.em.getTransaction().begin();
        // Primero persistimos la entidad relacionada para cumplir la FK de Prueba.
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + UUID.randomUUID());
        em.persist(tipoPrueba);

        Prueba nuevo = new Prueba(UUID.randomUUID());
        nuevo.setNombre("Prueba 1");
        nuevo.setIndicaciones("Indicaciones 1");
        nuevo.setPuntajeMaximo(new BigDecimal("20.00"));
        nuevo.setNotaAprobacion(new BigDecimal("12.00"));
        nuevo.setDuracion(90);
        nuevo.setFechaCreacion(OffsetDateTime.now());
        nuevo.setIdTipoPrueba(tipoPrueba);

        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        // Validamos que realmente se haya insertado un registro.
        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear Prueba entidad null");
        // Dejamos que falle por la validacion de entidad nula.
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crear(null);
        });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear Prueba Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.crear(new Prueba(UUID.randomUUID()));
        });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar prueba exitoso");
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar prueba Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar prueba Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId prueba exitoso");

        // Arrange: creamos y persistimos una Prueba completa para luego consultarla por id.
        cut.em.getTransaction().begin();
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + UUID.randomUUID());
        em.persist(tipoPrueba);

        Prueba nuevo = new Prueba(UUID.randomUUID());
        nuevo.setNombre("Prueba " + UUID.randomUUID());
        nuevo.setIndicaciones("Indicaciones de prueba");
        nuevo.setPuntajeMaximo(new BigDecimal("10.00"));
        nuevo.setNotaAprobacion(new BigDecimal("6.00"));
        nuevo.setDuracion(60);
        nuevo.setFechaCreacion(OffsetDateTime.now());
        nuevo.setIdTipoPrueba(tipoPrueba);
        em.persist(nuevo);
        cut.em.getTransaction().commit();

        // Act + Assert: buscamos por id y verificamos que los campos clave coincidan.
        UUID idBuscado = nuevo.getIdPrueba();
        Prueba encontrado = cut.buscarPorId(idBuscado);

        assertNotNull(encontrado);
        assertEquals(idBuscado, encontrado.getIdPrueba());
        assertEquals(nuevo.getNombre(), encontrado.getNombre());
        assertEquals(nuevo.getIndicaciones(), encontrado.getIndicaciones());
        assertEquals(nuevo.getPuntajeMaximo(), encontrado.getPuntajeMaximo());
        assertEquals(nuevo.getNotaAprobacion(), encontrado.getNotaAprobacion());
        System.out.println("IdPrueba: " + idBuscado);
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId prueba null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorId(null);
        });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId prueba em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorId(UUID.randomUUID());
        });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar Prueba entidad no guardada");

        cut.em.getTransaction().begin();
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + UUID.randomUUID());
        em.persist(tipoPrueba);

        UUID idEliminado = UUID.randomUUID();
        Prueba eliminado = new Prueba(idEliminado);
        eliminado.setNombre("Prueba eliminar no existente");
        eliminado.setPuntajeMaximo(new BigDecimal("10.00"));
        eliminado.setNotaAprobacion(new BigDecimal("6.00"));
        eliminado.setFechaCreacion(OffsetDateTime.now());
        eliminado.setIdTipoPrueba(tipoPrueba);

        // No existe en BD; eliminar no debe lanzar excepcion y debe seguir null al buscar.
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar Prueba exitoso");

        cut.em.getTransaction().begin();
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + UUID.randomUUID());
        em.persist(tipoPrueba);

        Prueba eliminado = new Prueba(UUID.randomUUID());
        eliminado.setNombre("Prueba " + UUID.randomUUID());
        eliminado.setIndicaciones("Indicaciones de prueba");
        eliminado.setPuntajeMaximo(new BigDecimal("10.00"));
        eliminado.setNotaAprobacion(new BigDecimal("6.00"));
        eliminado.setDuracion(60);
        eliminado.setFechaCreacion(OffsetDateTime.now());
        eliminado.setIdTipoPrueba(tipoPrueba);
        em.persist(eliminado);
        cut.em.getTransaction().commit();

        UUID idEliminado = eliminado.getIdPrueba();

        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(12)
    @Test
    public void testEliminarEntidadNula() {
        System.out.println("eliminar prueba entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.eliminar(null);
        });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar prueba em nulo");
        cut.em = null;
        Prueba eliminado = new Prueba(UUID.randomUUID());
        assertThrows(IllegalStateException.class, () -> {
            cut.eliminar(eliminado);
        });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso() {
        System.out.println("actualizar prueba exitoso");

        // Arrange: persistimos una entidad base para luego hacer merge.
        cut.em.getTransaction().begin();
        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        tipoPrueba.setValor("Tipo " + UUID.randomUUID());
        em.persist(tipoPrueba);

        Prueba nuevo = new Prueba(UUID.randomUUID());
        nuevo.setNombre("Prueba " + UUID.randomUUID());
        nuevo.setIndicaciones("Indicaciones de prueba");
        nuevo.setPuntajeMaximo(new BigDecimal("10.00"));
        nuevo.setNotaAprobacion(new BigDecimal("6.00"));
        nuevo.setDuracion(60);
        nuevo.setFechaCreacion(OffsetDateTime.now());
        nuevo.setIdTipoPrueba(tipoPrueba);
        em.persist(nuevo);
        cut.em.getTransaction().commit();

        // Modificamos campos no llave para validar merge.
        nuevo.setNombre("Prueba actualizada");
        nuevo.setDuracion(120);

        cut.em.getTransaction().begin();
        Prueba actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getNombre(), actualizado.getNombre());
        assertEquals(nuevo.getDuracion(), actualizado.getDuracion());
        System.out.println("Nombre actualizado: " + actualizado.getNombre());
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar prueba entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.actualizar(null);
        });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar prueba em nulo");
        cut.em = null;
        Prueba actualizado = new Prueba(UUID.randomUUID());

        assertThrows(IllegalStateException.class, () -> {
            cut.actualizar(actualizado);
        });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso() {
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<Prueba> resultados = cut.buscarPorRango(0, 50);
        assertNotNull(resultados);
        assertEquals(esperado, resultados.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango prueba parametros no validos");
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
        System.out.println("buscarPorRango prueba em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorRango(0, 50);
        });
    }
}
