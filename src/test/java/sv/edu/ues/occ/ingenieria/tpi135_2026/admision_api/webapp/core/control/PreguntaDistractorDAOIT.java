package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;


import java.util.UUID;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaDistractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PreguntaDistractorPK;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreguntaDistractorDAOIT extends AbstractIntengrationDAOTest{

    PreguntaDAO preguntaDAO = new PreguntaDAO();
    DistractorDAO distractorDAO = new DistractorDAO();

    PreguntaDistractorDAO cut = new PreguntaDistractorDAO();

    Pregunta pregunta = new Pregunta(UUID.randomUUID());
    
    Distractor distractor = new Distractor(UUID.randomUUID());
    Distractor distractor2 = new Distractor(UUID.randomUUID());

    PreguntaDistractorPK pk = new PreguntaDistractorPK(pregunta.getIdPregunta(), distractor.getIdDistractor());
    PreguntaDistractor preguntaDistractor = new PreguntaDistractor(pregunta, distractor); 
    PreguntaDistractorPK pk2 = new PreguntaDistractorPK(pregunta.getIdPregunta(), distractor2.getIdDistractor());
    PreguntaDistractor preguntaDistractor2 = new PreguntaDistractor(pregunta, distractor2);

    @BeforeEach
    public void setUp(){
        cut.em = em;
        preguntaDAO.em = em;
        distractorDAO.em = em;

        pregunta.setValor("¿Cuánto es 2 + 2?");
        pregunta.setActivo(true);

        distractor.setValor("4");
        distractor.setActivo(true);

        distractor2.setValor("5");
        distractor2.setActivo(true);

        preguntaDistractor.setCorrecto(true);
        preguntaDistractor2.setCorrecto(false);
    }

    public void crearContexto(){
        em.getTransaction().begin();
        preguntaDAO.crear(pregunta);
        distractorDAO.crear(distractor);
        distractorDAO.crear(distractor2);
        em.getTransaction().commit();
    }

    @Order(1)
    @Test
    public void testCrear(){
        crearContexto();
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.crear(preguntaDistractor);
        cut.crear(preguntaDistractor2);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues > registros);
    }

    @Order(2)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try{
            cut.crear(preguntaDistractor);
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
    public void testBuscarPorId(){
        cut.em = em;
        PreguntaDistractor resultado = cut.buscarPorId(pk);
        assertTrue(resultado != null);
        assertEquals(resultado, preguntaDistractor);
    }

    @Order(5)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(pk);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(6)
    @Test
    public void testBuscarPorIdParametroNull(){
        cut.em = em;
        try{
            cut.buscarPorId(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(7)
    @Test
    public void testBuscarPorRango(){
        cut.em = em;
        List<PreguntaDistractor> resultado = cut.buscarPorRango(0, 10);
        assertTrue((resultado.size()) >= 2);
    }

    @Order(8)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(9)
    @Test
    public void testBuscarPorRangoFirstNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(-1, 10);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(10)
    @Test
    public void testBuscarPorRangoMaxNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(0, -1);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(11)
    @Test
    public void testBuscarPorIdPregunta(){
        cut.em = em;
        List<PreguntaDistractor> resultado = cut.buscarPorIdPregunta(pregunta.getIdPregunta());
        assertTrue(resultado.size() >= 2);
        assertTrue(resultado.contains(preguntaDistractor));
        assertTrue(resultado.contains(preguntaDistractor2));
    }

    @Order(12)
    @Test
    public void testBuscarPorIdPreguntaNull(){
        cut.em = em;
        try {
            cut.buscarPorIdPregunta(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("El idPregunta no puede ser nulo", e.getMessage());
        }
    }

    @Order(13)
    @Test
    public void testBuscarPorIdPreguntaEmNull(){
        cut.em = null;
        try {
            cut.buscarPorIdPregunta(pregunta.getIdPregunta());
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Order(14)
    @Test
    public void testActualizar(){
        cut.em = em;
        Long registros = cut.contar();
        preguntaDistractor.setCorrecto(false);
        PreguntaDistractor resultado = cut.actualizar(preguntaDistractor);
        Long registrosDespues = cut.contar();
        assertTrue(resultado != null);
        assertTrue(registrosDespues == registros);
        assertEquals(resultado, preguntaDistractor);
    }

    @Order(15)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try{
            cut.actualizar(preguntaDistractor);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(16)
    @Test
    public void testActualizarParametroNull(){
        cut.em = em;
        try{
            cut.actualizar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(17)
    @Test
    public void testEliminar(){
        cut.em = em;
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(preguntaDistractor);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(registrosDespues < registros);
    }

    @Order(18)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try{
            cut.eliminar(preguntaDistractor);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(19)
    @Test
    public void testEliminarParametroNull(){
        cut.em = em;
        try{
            cut.eliminar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(20)
    @Test
    public void testContarEmNull(){
        cut.em = null;
        try{
            cut.contar();
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(21)
    @Test
    public void testEliminarNoContained(){
        em.clear();
        cut.eliminar(preguntaDistractor2);
        PreguntaDistractor encontrado = cut.buscarPorId(pk2);
        assertNull(encontrado);
    }
}
