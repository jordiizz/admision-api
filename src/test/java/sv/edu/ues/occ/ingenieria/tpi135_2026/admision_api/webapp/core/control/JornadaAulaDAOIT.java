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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaAulaDAO;

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
    public void testBuscarPorJornadaYAula(){
        JornadaAula resultado = cut.buscarPorJornadaYAula(jornada.getIdJornada(), idAula.toString());
        assertEquals(resultado, jornadaAula);
    }

    @Order(3)
    @Test
    public void testListarPorJornada(){
        List<JornadaAula> resultados = cut.listarPorJornada(jornada.getIdJornada());
        assertTrue(resultados.size() >= 2);
        for (JornadaAula resultado: resultados) {
            assertEquals(resultado.getIdJornada().getIdJornada(), jornada.getIdJornada());
        }
    }

    @Order(4)
    @Test
    public void testBuscarPorId(){
        JornadaAula resultado = cut.buscarPorId(jornadaAula.getIdJornadaAula());
        assertEquals(resultado, jornadaAula);
    }

    @Order(5)
    @Test
    public void testBuscarPorRango(){
        List<JornadaAula> resultados = cut.buscarPorRango(0, 10);
        assertTrue(resultados.size() >= 3);
    }

    @Order(6)
    @Test
    public void testActualizar(){
        Long registros = cut.contar();
        jornadaAula.setIdAula(idAula2.toString());
        cut.actualizar(jornadaAula);
        JornadaAula resultado = cut.buscarPorId(jornadaAula.getIdJornadaAula());
        Long registrosDespues = cut.contar();
        assertEquals(registros, registrosDespues);
        assertTrue(resultado.getIdAula() != idAula.toString());
        assertEquals(resultado.getIdAula(), idAula2.toString());
    }

    @Order(7)
    @Test
    public void testEliminar(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(jornadaAula);
        JornadaAula resultado = cut.buscarPorId(jornadaAula.getIdJornadaAula());
        Long registrosDespues = cut.contar();
        assertTrue(registrosDespues < registros);
        assertTrue(resultado == null);
        em.getTransaction().commit();
    }
}
