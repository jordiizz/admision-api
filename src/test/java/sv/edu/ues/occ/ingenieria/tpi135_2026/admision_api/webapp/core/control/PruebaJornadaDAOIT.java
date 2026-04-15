package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import org.junit.jupiter.api.*;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PruebaJornadaDAOIT extends AbstractIntengrationDAOTest{

    TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());

    Prueba prueba = new Prueba(UUID.randomUUID());

    Jornada jornada = new Jornada(UUID.randomUUID());
    Jornada jornada2 = new Jornada(UUID.randomUUID());
    Jornada jornada3 = new Jornada(UUID.randomUUID());

    PruebaJornadaPK pk = new PruebaJornadaPK(prueba.getIdPrueba(), jornada.getIdJornada());
    PruebaJornada pruebaJornada = new PruebaJornada(prueba, jornada);
    PruebaJornadaPK pk2 = new PruebaJornadaPK(prueba.getIdPrueba(), jornada2.getIdJornada());
    PruebaJornada pruebaJornada2 = new PruebaJornada(prueba, jornada2);
    PruebaJornadaPK pk3 = new PruebaJornadaPK(prueba.getIdPrueba(), jornada3.getIdJornada());
    PruebaJornada pruebaJornada3 = new PruebaJornada(prueba, jornada3);


    PruebaDAO pruebaDAO = new PruebaDAO();
    JornadaDAO jornadaDAO = new JornadaDAO();
    TipoPruebaDAO tipoPruebaDAO = new TipoPruebaDAO();

    PruebaJornadaDAO cut = new PruebaJornadaDAO();

    @BeforeEach
    public void setUp(){
        cut.em = em;
        jornadaDAO.em = em;
        pruebaDAO.em = em;
        tipoPruebaDAO.em = em;

        tipoPrueba.setValor("INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
        tipoPrueba.setActivo(true);

        prueba.setNombre("Examen Nuevo Ingreso 2026");
        prueba.setPuntajeMaximo(new BigDecimal(100));
        prueba.setIndicaciones("Aprobado un puntaje mayor 60, menor a este y mayor a 30 pasa a segunda ronda");
        prueba.setFechaCreacion(OffsetDateTime.now());
        prueba.setNotaAprobacion(new BigDecimal(60));
        prueba.setIdTipoPrueba(tipoPrueba);
        prueba.setDuracion(120);

        jornada.setNombre("MATUTINA_PRIMERA_RONDA_2026");
        jornada.setFechaInicio(OffsetDateTime.now());
        jornada.setFechaFin(OffsetDateTime.now().plusHours(6));

        jornada2.setNombre("VESPERTINA_PRIMERA_RONDA_2026");
        jornada2.setFechaInicio(OffsetDateTime.now().plusHours(6));
        jornada2.setFechaFin(OffsetDateTime.now().plusHours(12));

        jornada3.setNombre("VESPERTINA_SEGUNDA_RONDA_2026");
        jornada3.setFechaInicio(OffsetDateTime.now().plusDays(6));
        jornada3.setFechaFin(OffsetDateTime.now().plusDays(6).plusHours(12));

    }

    public void crearContexto(){
        tipoPruebaDAO.crear(tipoPrueba);
        pruebaDAO.crear(prueba);
        jornadaDAO.crear(jornada);
        jornadaDAO.crear(jornada2);
        jornadaDAO.crear(jornada3);

    }

    @Order(1)
    @Test
    public void testCrear(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        crearContexto();
        cut.crear(pruebaJornada);
        cut.crear(pruebaJornada2);
        cut.crear(pruebaJornada3);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues > registros);
    }

    @Order(2)
    @Test
    public void testBuscarPorId(){
        PruebaJornada encontrado = cut.buscarPorId(pk);
        assertEquals(encontrado, pruebaJornada);
    }

    @Order(3)
    @Test
    public void testBuscarPorRango(){
        int first = 0;
        int max = 10;
        List<PruebaJornada> encontrados = cut.buscarPorRango(first, max);
        assertTrue(encontrados.size() >= 2);
    }

    @Order(4)
    @Test
    public void testEliminar(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(pruebaJornada2);
        PruebaJornada encontrado = cut.buscarPorId(pk2);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues < registros);
        assertNull(encontrado);
    }

    @Order(5)
    @Test
    public void testActualizar(){
        pruebaJornada.setIdJornada(jornada2);
        PruebaJornada actualizado = cut.actualizar(pruebaJornada);
        assertEquals(actualizado.getIdJornada(), jornada2);
    }


    @Order(6)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try{
            cut.crear(pruebaJornada);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
        cut.em = em;
    }

    @Order(7)
    @Test
    public void testCrearParametrosInvalidos(){
        try{
            cut.crear(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(8)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(pk);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
        cut.em = em;
    }

    @Order(9)
    @Test
    public void testBuscarPorIdParametroNull(){
        try{
            cut.buscarPorId(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(10)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
        cut.em = em;
    }

    @Order(11)
    @Test
    public void testBuscarPorRangoFirstNegativo(){
        try{
            cut.buscarPorRango(-1, 10);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(12)
    @Test
    public void testBuscarPorRangoMaxNegativo(){
        try{
            cut.buscarPorRango(0, -1);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(13)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try{
            cut.eliminar(pruebaJornada);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
        cut.em = em;
    }

    @Order(14)
    @Test
    public void testEliminarParametroNull(){
        try{
            cut.eliminar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(15)
    @Test
    public void testEliminarNotContained(){
        PruebaJornada pruebaJornadaNotContained = new PruebaJornada(pruebaJornada3.getIdPrueba(), pruebaJornada3.getIdJornada());
        cut.eliminar(pruebaJornadaNotContained);
        PruebaJornada encontrado = cut.buscarPorId(pk3);
        assertNull(encontrado);
    }

    @Order(16)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try{
            cut.actualizar(pruebaJornada);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
        cut.em = em;
    }

    @Order(17)
    @Test
    public void testActualizarParametroNull(){
        try{
            cut.actualizar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(18)
    @Test
    public void testContarEmNull(){
        cut.em = null;
        try{
            cut.contar();
        }catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

}
