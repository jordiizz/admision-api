package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;


import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JornadaAulaDAOIT extends AbstractIntengrationDAOTest{

    JornadaDAO jornadaDAO = new JornadaDAO();
    JornadaAulaDAO cut = new JornadaAulaDAO();

    JornadaAula jornadaAula = new JornadaAula(UUID.randomUUID());
    JornadaAula jornadaAula2 = new JornadaAula(UUID.randomUUID());
    JornadaAula jornadaAula3 = new JornadaAula(UUID.randomUUID());

    Jornada jornada = new Jornada(UUID.randomUUID());
    Jornada jornada2 = new Jornada(UUID.randomUUID());

    UUID idAula = UUID.randomUUID();
    UUID idAula2 = UUID.randomUUID();
    UUID idAula3 = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        jornadaDAO.em = em;
        cut.em = em;

        jornada.setNombre("MATUTINA_2026");
        jornada.setFechaInicio(OffsetDateTime.now());
        jornada.setFechaFin(OffsetDateTime.now().plusDays(2));

        jornada2.setNombre("VESPERTINA_2026");
        jornada2.setFechaInicio(OffsetDateTime.now().plusHours(8));
        jornada2.setFechaFin(OffsetDateTime.now().plusHours(8).plusDays(2));


        jornadaAula.setIdAula(idAula.toString());
        jornadaAula.setIdJornada(jornada);

        jornadaAula2.setIdAula(idAula2.toString());
        jornadaAula2.setIdJornada(jornada2);

        jornadaAula3.setIdAula(idAula3.toString());
        jornadaAula3.setIdJornada(jornada);
    }

    @Order(1)
    @Test
    public void testCrear() {

        em.getTransaction().begin();

        jornadaDAO.crear(jornada);
        jornadaDAO.crear(jornada2);

        Long registros = cut.contar();
        cut.crear(jornadaAula);
        cut.crear(jornadaAula2);
        cut.crear(jornadaAula3);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues > registros);
    }

    @Order(2)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try{
            cut.crear(jornadaAula);
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
    public void testBuscarPorJornadaYAula(){
        cut.em = em;
        JornadaAula resultado = cut.buscarPorJornadaYAula(jornada.getIdJornada(), idAula.toString());
        assertEquals(resultado, jornadaAula);
    }

    @Order(5)
    @Test
    public void testBuscarPorJornadaYAulaEmNull(){
        cut.em = null;
        try{
            cut.buscarPorJornadaYAula(jornada.getIdJornada(), idAula.toString());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(6)
    @Test
    public void testBuscarPorJornadaYAulaIdJornadaNull(){
        cut.em = em;
        try{
            cut.buscarPorJornadaYAula(null, idAula.toString());
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(7)
    @Test
    public void testBuscarPorJornadaYAulaIdAulaNull(){
        cut.em = em;
        try{
            cut.buscarPorJornadaYAula(jornada.getIdJornada(), null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(8)
    @Test
    public void testListarPorJornada(){
        cut.em = em;
        List<JornadaAula> resultados = cut.listarPorJornada(jornada.getIdJornada());
        assertTrue(resultados.size() >= 2);
        for (JornadaAula resultado: resultados) {
            assertEquals(resultado.getIdJornada().getIdJornada(), jornada.getIdJornada());
        }
    }

    @Order(9)
    @Test
    public void testListarPorJornadaEmNull(){
        cut.em = null;
        try{
            cut.listarPorJornada(jornada.getIdJornada());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(10)
    @Test
    public void testListarPorJornadaIdJornadaNull(){
        cut.em = em;
        try{
            cut.listarPorJornada(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(11)
    @Test
    public void testBuscarPorId(){
        cut.em = em;
        JornadaAula resultado = cut.buscarPorId(jornadaAula.getIdJornadaAula());
        assertEquals(resultado, jornadaAula);
    }

    @Order(12)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(jornadaAula.getIdJornadaAula());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(13)
    @Test
    public void testBuscarPorIdParametroNull(){
        cut.em = em;
        try{
            cut.buscarPorId(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(14)
    @Test
    public void testBuscarPorRango(){
        cut.em = em;
        List<JornadaAula> resultados = cut.buscarPorRango(0, 10);
        assertTrue(resultados.size() >= 3);
    }

    @Order(15)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(16)
    @Test
    public void testBuscarPorRangoFirstNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(-1, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoMaxNegativo(){
        cut.em = em;
        try{
            cut.buscarPorRango(0, -1);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(18)
    @Test
    public void testActualizar(){
        cut.em = em;
        Long registros = cut.contar();
        jornadaAula.setIdAula(idAula2.toString());
        cut.actualizar(jornadaAula);
        JornadaAula resultado = cut.buscarPorId(jornadaAula.getIdJornadaAula());
        Long registrosDespues = cut.contar();
        assertEquals(registros, registrosDespues);
        assertTrue(resultado.getIdAula() != idAula.toString());
        assertEquals(resultado.getIdAula(), idAula2.toString());
    }

    @Order(19)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try{
            cut.actualizar(jornadaAula);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(20)
    @Test
    public void testActualizarParametroNull(){
        cut.em = em;
        try{
            cut.actualizar(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(21)
    @Test
    public void testEliminar(){
        cut.em = em;
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(jornadaAula);
        JornadaAula resultado = cut.buscarPorId(jornadaAula.getIdJornadaAula());
        Long registrosDespues = cut.contar();
        assertTrue(registrosDespues < registros);
        assertTrue(resultado == null);
        em.getTransaction().commit();
    }

    @Order(22)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try{
            cut.eliminar(jornadaAula2);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(23)
    @Test
    public void testEliminarParametroNull(){
        cut.em = em;
        try{
            cut.eliminar(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(24)
    @Test
    public void testContarEmNull(){
        cut.em = null;
        try{
            cut.contar();
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(25)
    @Test
    public void testEliminarNoContained(){
        em.clear();
        cut.eliminar(jornadaAula2);
        JornadaAula encontrado = cut.buscarPorId(jornadaAula2.getIdJornadaAula());

        assertNull(encontrado);
    }

    @Order(26) // Siguiendo tu secuencia
    @Test
    public void testBuscarPorJornadaYAula_NoEncontrado() {
        cut.em = em;
        // Usamos un UUID aleatorio y un ID de aula que no existe en los registros creados
        UUID idInexistente = UUID.randomUUID();
        String aulaInexistente = "AULA-999";

        JornadaAula resultado = cut.buscarPorJornadaYAula(idInexistente, aulaInexistente);

        // Aquí validamos que la lógica del ternario devuelva null correctamente
        assertNull(resultado, "Debería retornar null cuando no existen registros coincidentes");
    }
}