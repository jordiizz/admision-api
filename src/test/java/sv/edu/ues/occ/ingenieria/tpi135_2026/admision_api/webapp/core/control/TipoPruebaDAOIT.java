package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.TipoPrueba;

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
    public void testActualizarTipoPruebaExitoso(){
        cantidadRegistros = cut.contar();
        tipoPrueba.setValor("ACTUALIZADO");
        TipoPrueba tipoPruebaActualizado = cut.actualizar(tipoPrueba);
        assertEquals(tipoPrueba, tipoPruebaActualizado);
        assertTrue(cantidadRegistrosDespues.equals(cantidadRegistros));
    }

    @Order(4)
    @Test
    public void testBuscarTipoPruebaPorIdExitoso(){
        TipoPrueba tipoPruebaEncontrado = cut.buscarPorId(tipoPrueba.getIdTipoPrueba());
        assertEquals(tipoPrueba, tipoPruebaEncontrado); 
    }

    @Order(5)
    @Test
    public void testBuscarTipoPruebaPorRangoExitoso(){
        em.getTransaction().begin();
        cut.crear(tipoPrueba2);
        cut.crear(tipoPrueba3);
        List<TipoPrueba> tipoPruebas = cut.buscarPorRango(0, 3);
        em.getTransaction().commit();
        assertTrue(tipoPruebas.size() == 3);
    }   

    @Order(6)
    @Test
    public void testEliminarTipoPruebaExitoso(){
        em.getTransaction().begin();
        cantidadRegistros = cut.contar();
        cut.eliminar(tipoPrueba);
        cut.eliminar(tipoPrueba2);
        cut.eliminar(tipoPrueba3);
        cantidadRegistrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(cantidadRegistrosDespues < cantidadRegistros);
    }

}
