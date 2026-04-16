package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaClaveDAOIT extends AbstractIntengrationDAOTest {

    PruebaClaveDAO cut = new PruebaClaveDAO();
    PruebaDAO pruebaDAO = new PruebaDAO();
    TipoPruebaDAO tipoPruebaDAO = new TipoPruebaDAO();

    TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
    Prueba prueba = new Prueba(UUID.randomUUID());
    PruebaClave pruebaClave = new PruebaClave(UUID.randomUUID());
    PruebaClave pruebaClave2 = new PruebaClave(UUID.randomUUID());
    PruebaClave pruebaClave3 = new PruebaClave(UUID.randomUUID());

    @BeforeEach
    public void setUp() {
        cut.em = em;
        pruebaDAO.em = em;
        tipoPruebaDAO.em = em;

        tipoPrueba.setValor("INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
        tipoPrueba.setActivo(true);

        prueba.setNombre("PRUEBA_ADMISION_2026");
        prueba.setIndicaciones("Indicaciones para la prueba de admision 2026");
        prueba.setDuracion(120);
        prueba.setIdTipoPrueba(tipoPrueba);
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setNotaAprobacion(new BigDecimal(60));
        prueba.setPuntajeMaximo(new BigDecimal(100));

        pruebaClave.setIdPrueba(prueba);
        pruebaClave.setNombreClave("PRIMERA_CLAVE");

        pruebaClave2.setIdPrueba(prueba);
        pruebaClave2.setNombreClave("SEGUNDA_CLAVE");

        pruebaClave3.setIdPrueba(prueba);
        pruebaClave3.setNombreClave("TERCERA_CLAVE");

    }

    public void crearContexto() {
        tipoPruebaDAO.crear(tipoPrueba);
        pruebaDAO.crear(prueba);
    }

    @Order(1)
    @Test
    public void testCrear() {
        em.getTransaction().begin();
        crearContexto();
        Long registros = cut.contar();
        cut.crear(pruebaClave);
        cut.crear(pruebaClave2);
        cut.crear(pruebaClave3);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(registrosDespues > registros);
    }

    @Order(2)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try{
            cut.crear(pruebaClave);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(3)
    @Test
    public void testCrearParametrosInvalidos(){
        cut.em = em;
        try{
            cut.crear(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(4)
    @Test
    public void testBuscarPorId() {
        cut.em = em;
        PruebaClave resultado = cut.buscarPorId(pruebaClave.getIdPruebaClave());
        assertTrue(resultado.equals(pruebaClave));
    }

    @Order(5)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(pruebaClave.getIdPruebaClave());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(6)
    @Test
    public void testBuscarPorIdParametroNull(){
        cut.em = em;
        try{
            cut.buscarPorId(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(7)
    @Test
    public void testListarClavesPorPrueba() {
        cut.em = em;
        List<PruebaClave> resultados = cut.listarClavesPorPrueba(prueba.getIdPrueba());
        assertTrue(resultados.size() >= 3);
        for (PruebaClave resultado : resultados) {
            assertTrue(resultado.getIdPrueba().getIdPrueba().equals(prueba.getIdPrueba()));
        }
    }

    @Order(8)
    @Test
    public void testListarClavesPorPruebaEmNull(){
        cut.em = null;
        try{
            cut.listarClavesPorPrueba(prueba.getIdPrueba());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(9)
    @Test
    public void testListarClavesPorPruebaIdPruebaNull(){
        cut.em = em;
        try{
            cut.listarClavesPorPrueba(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(10)
    @Test
    public void testBuscarPorRango() {
        cut.em = em;
        List<PruebaClave> resultados = cut.buscarPorRango(0, 10);
        assertTrue(resultados.size() >= 3);
    }

    @Order(11)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(12)
    @Test
    public void testBuscarPorRangoFirstNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(-1, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(13)
    @Test
    public void testBuscarPorRangoMaxNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(0, -1);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(14)
    @Test
    public void testActualizar() {
        cut.em = em;
        Long registros = cut.contar();
        pruebaClave.setNombreClave("CLAVE_ACTUALIZADA");
        cut.actualizar(pruebaClave);
        Long registrosDespues = cut.contar();
        PruebaClave resultado = cut.buscarPorId(pruebaClave.getIdPruebaClave());
        assertEquals(resultado.getNombreClave(), "CLAVE_ACTUALIZADA");
        assertEquals(registrosDespues, registros);
    }

    @Order(15)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try{
            cut.actualizar(pruebaClave);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(16)
    @Test
    public void testActualizarParametroNull(){
        cut.em = em;
        try{
            cut.actualizar(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(17)
    @Test
    public void testEliminar() {
        cut.em = em;
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(pruebaClave3);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(registrosDespues < registros);
    }

    @Order(18)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try{
            cut.eliminar(pruebaClave2);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(19)
    @Test
    public void testEliminarParametroNull(){
        cut.em = em;
        try{
            cut.eliminar(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(20)
    @Test
    public void testContarEmNull(){
        cut.em = null;
        try{
            cut.contar();
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(21)
    @Test
    public void testEliminarNoContained(){
        em.clear();
        cut.eliminar(pruebaClave2);
        PruebaClave encontrado = cut.buscarPorId(pruebaClave2.getIdPruebaClave());
        assertNull(encontrado);
    }
}
