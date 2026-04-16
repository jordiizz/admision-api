package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoPruebaDAOIT extends AbstractIntengrationDAOTest {

    TipoPruebaDAO cut;
    TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
    TipoPrueba tipoPrueba2 = new TipoPrueba(UUID.randomUUID());
    TipoPrueba tipoPrueba3 = new TipoPrueba(UUID.randomUUID());
    Long cantidadRegistros;
    Long cantidadRegistrosDespues;

    @BeforeEach
    public void setup() {
        cut = new TipoPruebaDAO();
        cut.em = em;
        tipoPrueba.setValor("NUEVO_INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
        tipoPrueba.setActivo(true);
        tipoPrueba2.setValor("INGRESO_JOVENES_TALENTO_SEGUNDA_RONDA");
        tipoPrueba2.setActivo(true);
        tipoPrueba3.setValor("INGRESO_JOVENES_TALENTO_PRIMERA_RONDA");
        tipoPrueba3.setActivo(true);
    }


    @Order(1)
    @Test
    public void testContarTipoPruebaExitoso(){
        cantidadRegistros = cut.contar();
        assertTrue(cantidadRegistros == 0);
    }

    @Order(2)
    @Test
    public void testCrearTipoPruebaExitoso(){
        em.getTransaction().begin();
        cantidadRegistros = cut.contar();
        cut.crear(tipoPrueba);
        cantidadRegistrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(cantidadRegistrosDespues > cantidadRegistros);
    }

    @Order(3)
    @Test
    public void testCrearTipoPruebaEmNull(){
        cut.em = null;
        try{
            cut.crear(tipoPrueba2);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(4)
    @Test
    public void testCrearTipoPruebaParametrosInvalidos(){
        try{
            cut.crear(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(5)
    @Test
    public void testActualizarTipoPruebaExitoso(){
        cut.em = em;
        cantidadRegistros = cut.contar();
        tipoPrueba.setValor("ACTUALIZADO");
        TipoPrueba tipoPruebaActualizado = cut.actualizar(tipoPrueba);
        assertEquals(tipoPrueba, tipoPruebaActualizado);
        assertTrue(cantidadRegistrosDespues.equals(cantidadRegistros));
    }

    @Order(6)
    @Test
    public void testActualizarTipoPruebaEmNull(){
        cut.em = null;
        try{
            cut.actualizar(tipoPrueba);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(7)
    @Test
    public void testActualizarTipoPruebaParametroNull(){
        cut.em = em;
        try{
            cut.actualizar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(8)
    @Test
    public void testBuscarTipoPruebaPorIdExitoso(){
        TipoPrueba tipoPruebaEncontrado = cut.buscarPorId(tipoPrueba.getIdTipoPrueba());
        assertEquals(tipoPrueba, tipoPruebaEncontrado);
    }

    @Order(9)
    @Test
    public void testBuscarTipoPruebaPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(tipoPrueba.getIdTipoPrueba());
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(10)
    @Test
    public void testBuscarTipoPruebaPorIdParametroNull(){
        cut.em = em;
        try{
            cut.buscarPorId(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(11)
    @Test
    public void testBuscarTipoPruebaPorRangoExitoso(){
        cut.em = em;
        em.getTransaction().begin();
        cut.crear(tipoPrueba2);
        cut.crear(tipoPrueba3);
        List<TipoPrueba> tipoPruebas = cut.buscarPorRango(0, 3);
        em.getTransaction().commit();
        assertTrue(tipoPruebas.size() == 3);
    }

    @Order(12)
    @Test
    public void testBuscarTipoPruebaPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(13)
    @Test
    public void testBuscarTipoPruebaPorRangoFirstNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(-1, 10);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(14)
    @Test
    public void testBuscarTipoPruebaPorRangoMaxNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(0, -1);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(15)
    @Test
    public void testEliminarTipoPruebaExitoso(){
        cut.em = em;
        em.getTransaction().begin();
        cantidadRegistros = cut.contar();
        cut.eliminar(tipoPrueba);
        cut.eliminar(tipoPrueba2);
        cantidadRegistrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(cantidadRegistrosDespues < cantidadRegistros);
    }

    @Order(16)
    @Test
    public void testEliminarTipoPruebaEmNull(){
        cut.em = null;
        try{
            cut.eliminar(tipoPrueba);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(17)
    @Test
    public void testEliminarTipoPruebaParametroNull(){
        try{
            cut.eliminar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(18)
    @Test
    public void testContarTipoPruebaEmNull(){
        cut.em = null;
        try{
            cut.contar();
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(19)
    @Test
    public void testEliminarNoContained(){
        em.clear();
        cut.eliminar(tipoPrueba3);
        TipoPrueba encontrado = cut.buscarPorId(tipoPrueba3.getIdTipoPrueba());

        assertNull(encontrado);
    }

}
