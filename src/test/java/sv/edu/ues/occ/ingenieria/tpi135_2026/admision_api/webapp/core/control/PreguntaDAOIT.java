package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;


import java.util.UUID;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreguntaDAOIT extends AbstractIntengrationDAOTest{

    PreguntaDAO cut = new PreguntaDAO();

    Pregunta pregunta = new Pregunta(UUID.randomUUID());
    Pregunta pregunta2 = new Pregunta(UUID.randomUUID());
    Pregunta pregunta3 = new Pregunta(UUID.randomUUID());


    @BeforeEach
    public void setUp() {
        cut.em = em;

        pregunta.setValor("¿Cuánto es 2 + 2?");
        pregunta.setActivo(true);

        pregunta2.setValor("¿Cuál es la capital de Francia?");
        pregunta2.setActivo(true);

        pregunta3.setValor("¿Quién escribió 'Cien años de soledad'?");
        pregunta3.setActivo(true);
    }

    @Order(1)
    @Test
    public void testCrear(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.crear(pregunta);
        cut.crear(pregunta2);
        cut.crear(pregunta3); 
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues > registros);
    }

    @Order(2)
    @Test
    public void testCrearNull(){
        try {
            cut.crear(null);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("entity no puede ser nulo", ex.getMessage());
        }
    }

    @Order(3)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try {
            cut.crear(pregunta);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Order(4)
    @Test
    public void testBuscarPorId(){
        Pregunta encontrada = cut.buscarPorId(pregunta.getIdPregunta());
        assertEquals(encontrada, pregunta);
    }

    @Order(5)
    @Test
    public void testBuscarPorIdNull(){
        try {
            cut.buscarPorId(null);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("id no puede ser nulo", ex.getMessage());
        }
    }

    @Order(7)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try {
            cut.buscarPorId(pregunta.getIdPregunta());
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Order(8)
    @Test
    public void testBuscarPorRango(){
        int first = 0;
        int max = 10;
        List<Pregunta> preguntas = cut.buscarPorRango(first, max);
        assertTrue(preguntas.size() >= 3);
    }

    @Order(9)
    @Test
    public void testBuscarPorRangoParametroFirstInvalido(){
        try {
            cut.buscarPorRango(-1, 10);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("Parametro no válido: first o max", ex.getMessage());
        }   
    }

    @Order(10)
    @Test
    public void testBuscarPorRangoParametroMaxInvalido(){
        try {
            cut.buscarPorRango(0, -1);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("Parametro no válido: first o max", ex.getMessage());
        }   
    }

    @Order(11)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try {
            cut.buscarPorRango(0, 10);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Order(12)
    @Test 
    public void testActualizar(){
        String nuevoValor = "¿Cuánto es 3 + 3?";
        pregunta.setValor(nuevoValor);
        Pregunta actualizada = cut.actualizar(pregunta);
        assertEquals(nuevoValor, actualizada.getValor());
    }

    @Order(13)
    @Test
    public void testActualizarNull(){
        try {
            cut.actualizar(null);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("entity no puede ser nulo", ex.getMessage());
        }
    }

    @Order(14)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try {
            cut.actualizar(pregunta);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Order(15)
    @Test
    public void testEliminar(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(pregunta);
        cut.eliminar(pregunta2);
        cut.eliminar(pregunta3);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues == registros - 3);
    } 

    @Order(16)
    @Test
    public void testEliminarNull(){
        try {
            cut.eliminar(null);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("entity no puede ser nulo", ex.getMessage());
        }
    }

    @Order(17)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try {
            cut.eliminar(pregunta);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }
    @Order(18)
    @Test
    public void testEliminarNoContained(){
        Pregunta preguntaNoContained = new Pregunta(pregunta.getIdPregunta());
        cut.eliminar(preguntaNoContained);
     
    }

    @Order(19)
    @Test
    public void testCountEmNull(){
        cut.em = null;
        try {
            cut.contar();
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }
}
