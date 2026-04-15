package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testBuscarPorId(){
        PreguntaDistractor resultado = cut.buscarPorId(pk);
        assertTrue(resultado != null);
        assertEquals(resultado, preguntaDistractor);
    }

    @Order(3)
    @Test
    public void testBuscarPorRango(){
        List<PreguntaDistractor> resultado = cut.buscarPorRango(0, 10);
        assertTrue((resultado.size()) >= 2);
    }

    @Order(4)
    @Test
    public void testBuscarPorIdPregunta(){
        List<PreguntaDistractor> resultado = cut.buscarPorIdPregunta(pregunta.getIdPregunta());
        assertTrue(resultado.size() >= 2);
        assertTrue(resultado.contains(preguntaDistractor));
        assertTrue(resultado.contains(preguntaDistractor2));
    }

    @Order(5)
    @Test
    public void testBuscarPorIdPreguntaNull(){
        try {
            cut.buscarPorIdPregunta(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("El idPregunta no puede ser nulo", e.getMessage());
        }
    }

    @Order(6)
    @Test
    public void testBuscarPorIdPreguntaException(){
        try {
            cut.em = null;
            cut.buscarPorIdPregunta(pregunta.getIdPregunta());
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Order(7)
    @Test
    public void testActualizar(){
        Long registros = cut.contar();
        preguntaDistractor.setCorrecto(false);
        PreguntaDistractor resultado = cut.actualizar(preguntaDistractor);
        Long registrosDespues = cut.contar();
        assertTrue(resultado != null);
        assertTrue(registrosDespues == registros);
        assertEquals(resultado, preguntaDistractor);
    }

    @Order(8)
    @Test
    public void testEliminar(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(preguntaDistractor);
        cut.eliminar(preguntaDistractor2);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(registrosDespues < registros);
    }

}
