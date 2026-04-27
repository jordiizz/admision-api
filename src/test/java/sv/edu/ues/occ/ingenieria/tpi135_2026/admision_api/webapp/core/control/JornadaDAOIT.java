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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JornadaDAOIT extends AbstractIntengrationDAOTest {

    TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
    Jornada jornada = new Jornada(UUID.randomUUID());
    Jornada jornada2 = new Jornada(UUID.randomUUID());
    PruebaJornada pruebaJornada = new PruebaJornada();
    PruebaJornada pruebaJornada2 = new PruebaJornada();
    Prueba prueba = new Prueba(UUID.randomUUID());

    PruebaJornadaDAO pruebaJornadaDAO;
    TipoPruebaDAO tipoPruebaDAO;;
    JornadaDAO cut; // Class under test
    PruebaDAO pruebaDAO;

    @BeforeEach
    public void setup() {
        cut = new JornadaDAO();
        cut.em = em;
        pruebaDAO = new PruebaDAO();
        pruebaDAO.em = em;
        pruebaJornadaDAO = new PruebaJornadaDAO();
        pruebaJornadaDAO.em =em;
        tipoPruebaDAO = new TipoPruebaDAO();
        tipoPruebaDAO.em = em;

        jornada.setNombre("Jornada 1");
        jornada.setFechaInicio(OffsetDateTime.now());
        jornada.setFechaFin(OffsetDateTime.now().plusDays(1));

        jornada2.setNombre("VESPERTINA_PRIMERA_RONDA");
        jornada2.setFechaInicio(OffsetDateTime.now().plusDays(1));
        jornada2.setFechaFin(OffsetDateTime.now().plusDays(1).plusHours(8));

        tipoPrueba.setValor("INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
        tipoPrueba.setActivo(true);

        prueba.setNombre("NUEVO_INGRESO_2026");
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setPuntajeMaximo(new BigDecimal(100));
        prueba.setDuracion(120);
        prueba.setIndicaciones("Indicaciones");
        prueba.setNotaAprobacion(new BigDecimal(50));


    }

    public void crearContexto(){
        tipoPruebaDAO.crear(tipoPrueba);
        prueba.setIdTipoPrueba(tipoPrueba);
        pruebaDAO.crear(prueba);

        pruebaJornada.setIdJornada(jornada);
        pruebaJornada.setIdPrueba(prueba);

        pruebaJornada2.setIdJornada(jornada2);
        pruebaJornada2.setIdPrueba(prueba);

        pruebaJornadaDAO.crear(pruebaJornada);
        pruebaJornadaDAO.crear(pruebaJornada2);
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear jornada exitoso");

        em.getTransaction().begin();
        cut.crear(jornada);
        cut.crear(jornada2);

        Long resultado = cut.contar();
        assertEquals(2, resultado);
        System.out.println("Resultado: " + resultado);

        crearContexto();
        em.getTransaction().commit();

    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear Jornada entidad null");
        // Dejamos que falle por la validación de la entidad nula
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crear(null);
        });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear Jornada Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.crear(new Jornada(UUID.randomUUID()));
        });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar jornada exitoso");
        Long resultado = cut.contar();
        assertEquals(2, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar jornada Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar jornada Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId jornada exitoso");
        // Creamos una nueva jornada para asegurarnos de que existe en la base de datos
        UUID idBuscado = UUID.randomUUID();
        Jornada nuevo = new Jornada(idBuscado);
        nuevo.setNombre("Jornada 2");
        nuevo.setFechaInicio(OffsetDateTime.now().plusDays(2));
        nuevo.setFechaFin(OffsetDateTime.now().plusDays(3));

        // Realizamos la transacción para guardar la nueva jornada
        em.getTransaction().begin();
        cut.crear(nuevo);
        em.getTransaction().commit();
        // Buscamos por el ID de la jornada recién creada
        Jornada encontrado = cut.buscarPorId(idBuscado);

        assertNotNull(encontrado);
        assertEquals(idBuscado, encontrado.getIdJornada());
        assertEquals(nuevo.getNombre(), encontrado.getNombre());
        assertEquals(nuevo.getFechaInicio(), encontrado.getFechaInicio());
        assertEquals(nuevo.getFechaFin(), encontrado.getFechaFin());
        System.out.println("Id: " + idBuscado);
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId jornada null");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.buscarPorId(null);
        });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId jornada em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorId(UUID.randomUUID());
        });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar Jornada entidad no guardada");
        // Creamos una nueva jornada pero no la guardamos en la base de datos
        UUID idEliminado = UUID.randomUUID();
        Jornada eliminado = new Jornada(idEliminado);
        eliminado.setNombre("Jornada 3");
        eliminado.setFechaInicio(OffsetDateTime.now().plusDays(4));
        eliminado.setFechaFin(OffsetDateTime.now().plusDays(5));

        // ELiminamos sin guardar primero, lo que debería hacer el merge y luego eliminar
        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar Jornada exitoso");
        UUID idEliminado = UUID.randomUUID();
        Jornada eliminado = new Jornada(idEliminado);
        eliminado.setNombre("Jornada 4");
        eliminado.setFechaInicio(OffsetDateTime.now().plusDays(6));
        eliminado.setFechaFin(OffsetDateTime.now().plusDays(7));

        // Guardamos
        cut.em.getTransaction().begin();
        cut.crear(eliminado);
        cut.em.getTransaction().commit();

        // eliminamos
        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(12)
    @Test
    public void testEliminarEntidadNula() {
        System.out.println("eliminar jornada entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.eliminar(null);
        });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar jornada em nulo");
        cut.em = null;
        Jornada eliminado = new Jornada(UUID.randomUUID());
        assertThrows(IllegalStateException.class, () -> {
            cut.eliminar(eliminado);
        });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso() {
        System.out.println("actualizar jornada exitoso");
        Jornada nuevo = new Jornada(UUID.randomUUID());
        nuevo.setNombre("Jornada 5");
        nuevo.setFechaInicio(OffsetDateTime.now().plusDays(8));
        nuevo.setFechaFin(OffsetDateTime.now().plusDays(9));

        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        // Modificar la entidad
        nuevo.setNombre("Actualizado");
        cut.em.getTransaction().begin();
        Jornada actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getNombre(), actualizado.getNombre());
        System.out.println("Nombre actualizado: " + actualizado.getNombre());
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar jornada entidad nula");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.actualizar(null);
        });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar jornada em nulo");
        // Hacemos la entity manager null
        cut.em = null;
        Jornada actualizado = new Jornada(UUID.randomUUID());

        assertThrows(IllegalStateException.class, () -> {
            cut.actualizar(actualizado);
        });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso() {
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<Jornada> jornadas = cut.buscarPorRango(0, 50);
        assertNotNull(jornadas);
        assertEquals(esperado, jornadas.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango jornada parametros no validos");
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
        System.out.println("buscarPorRango jornada parametros no validos");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {
            cut.buscarPorRango(0, 50);
        });
    }

    @Order(20)
    @Test
    public void listarPorIdPrueba(){
        System.out.println("listarPorIdPrueba exitoso");
        List<Jornada> jornadas = cut.listarPorIdPrueba(prueba.getIdPrueba(), 0, 10);
        assertNotNull(jornadas);
        assertEquals(2, jornadas.size());
    }

    @Order(21)
    @Test
    public void listarPorIdPruebaParametrosNoValidos(){
        System.out.println("listarPorIdPrueba parametros no validos");
        assertThrows(IllegalArgumentException.class, () -> {
            cut.listarPorIdPrueba(null, 0, 10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.listarPorIdPrueba(prueba.getIdPrueba(), -1, 10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.listarPorIdPrueba(prueba.getIdPrueba(), 0, -1);
        });
    }

     @Order(22)
     @Test
    public void listarPorIdPruebaStateExcepcion(){
        cut.em = null;
         assertThrows(IllegalStateException.class, () -> {
             cut.listarPorIdPrueba(prueba.getIdPrueba(), 0, 10);
         });
     }
}
