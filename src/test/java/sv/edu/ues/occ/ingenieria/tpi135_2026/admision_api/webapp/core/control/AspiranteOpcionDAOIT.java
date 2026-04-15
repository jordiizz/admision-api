package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import org.junit.jupiter.api.*;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Aspirante;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AspiranteOpcionDAOIT extends AbstractIntengrationDAOTest{

    AspiranteOpcionDAO cut = new AspiranteOpcionDAO();
    AspiranteDAO aspiranteDAO = new AspiranteDAO();

    Aspirante aspirante = new Aspirante(UUID.randomUUID());

    AspiranteOpcion aspiranteOpcion = new AspiranteOpcion(UUID.randomUUID());
    AspiranteOpcion aspiranteOpcion2 = new AspiranteOpcion(UUID.randomUUID());

    String idOpcion = UUID.randomUUID().toString();
    String idOpcion2 = UUID.randomUUID().toString();

    @BeforeEach
    public void setUp(){
        cut.em = em;
        aspiranteDAO.em = em;

        aspirante.setNombres("José");
        aspirante.setApellidos("Barraza");
        aspirante.setCorreo("jose.barraza@email.com");
        aspirante.setDocumentoIdentidad("000000-0");
        aspirante.setFechaNacimiento(LocalDate.of(2000, 12, 1));
        aspirante.setFechaCreacion(OffsetDateTime.now());

        aspiranteOpcion.setIdAspirante(aspirante);
        aspiranteOpcion.setIdOpcion(idOpcion);
        aspiranteOpcion.setPrioridad(1);
        aspiranteOpcion.setFechaCreacion(OffsetDateTime.now());

        aspiranteOpcion2.setIdAspirante(aspirante);
        aspiranteOpcion2.setIdOpcion(idOpcion2);
        aspiranteOpcion2.setPrioridad(2);
        aspiranteOpcion2.setFechaCreacion(OffsetDateTime.now());

    }

    public void crearContexto(){
        aspiranteDAO.crear(aspirante);
    }

    @Order(1)
    @Test
    public void testCrear(){
        em.getTransaction().begin();
        crearContexto();
        Long registros = cut.contar();
        cut.crear(aspiranteOpcion);
        cut.crear(aspiranteOpcion2);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
    }

    @Order(2)
    @Test
    public void testBuscarPorAspiranteRango(){
        List<AspiranteOpcion> encontrados = cut.buscarPorAspiranteRango(aspirante.getIdAspirante(), 0 , 10);
        assertTrue(encontrados.size() >= 2);
    }

    @Order(3)
    @Test
    public void testContarPorAspirante(){
        Long registros = cut.contarPorAspirante(aspirante.getIdAspirante());
        assertTrue(registros >= 2);
    }

    @Order(4)
    @Test
    public void testBuscarPorIdYAspirante(){
        AspiranteOpcion encontrado = cut.buscarPorIdYAspirante(aspiranteOpcion.getIdAspiranteOpcion(), aspirante.getIdAspirante());
        assertNotNull(encontrado);
        assertEquals(encontrado, aspiranteOpcion);
    }

    @Order(5)
    @Test
    public void testBuscarPorId(){
        AspiranteOpcion encontrado = cut.buscarPorId(aspiranteOpcion.getIdAspiranteOpcion());
        assertNotNull(encontrado);
        assertEquals(encontrado, aspiranteOpcion);
    }

    @Order(6)
    @Test
    public void testBuscarPorRango(){
        List<AspiranteOpcion> encontrados = cut.buscarPorRango(0, 10);
        assertNotNull(encontrados);
        assertTrue(encontrados.size() >= 2);
    }

    @Order(7)
    @Test
    public void testActualizar(){
        aspiranteOpcion.setPrioridad(3);
        AspiranteOpcion actualizado = cut.actualizar(aspiranteOpcion);

        assertEquals(3, actualizado.getPrioridad());
    }

    @Order(8)
    @Test
    public void testEliminar(){
        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.eliminar(aspiranteOpcion2);
        Long registrosDespues = cut.contar();
        AspiranteOpcion encontrado = cut.buscarPorId(aspiranteOpcion2.getIdAspiranteOpcion());
        em.getTransaction().commit();

        assertNull(encontrado);
        assertTrue(registrosDespues < registros);
    }

    @Order(9)
    @Test
    public void testEliminarNoContained(){
        AspiranteOpcion aspiranteOpcionNoContained = new AspiranteOpcion(aspiranteOpcion.getIdAspiranteOpcion());
        cut.eliminar(aspiranteOpcionNoContained);
        AspiranteOpcion encontrado = cut.buscarPorId(aspiranteOpcion.getIdAspiranteOpcion());

        assertNull(encontrado);
    }

    @Order(10)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try{
            cut.crear(new AspiranteOpcion(UUID.randomUUID()));
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(11)
    @Test
    public void testCrearParametrosInvalidos(){
        try{
            cut.crear(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(12)
    @Test
    public void testBuscarPorAspiranteRangoIdAspiranteNull(){
        try{
            cut.buscarPorAspiranteRango(null, 0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(13)
    @Test
    public void testBuscarPorAspiranteRangoFirstNegativo(){
        try{
            cut.buscarPorAspiranteRango(aspirante.getIdAspirante(), -1, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(14)
    @Test
    public void testBuscarPorAspiranteRangoMaxNegativo(){
        try{
            cut.buscarPorAspiranteRango(aspirante.getIdAspirante(), 0, -1);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(15)
    @Test
    public void testBuscarPorAspiranteRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorAspiranteRango(aspirante.getIdAspirante(), 0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(16)
    @Test
    public void testContarPorAspiranteParametrosInvalidos(){
        try{
            cut.contarPorAspirante(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(17)
    @Test
    public void testContarPorAspiranteEmNull(){
        cut.em = null;
        try{
            cut.contarPorAspirante(aspirante.getIdAspirante());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(18)
    @Test
    public void testBuscarPorIdYAspiranteIdAspiranteOpcionNull(){
        try{
            cut.buscarPorIdYAspirante(null, aspirante.getIdAspirante());
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(19)
    @Test
    public void testBuscarPorIdYAspiranteIdAspiranteNull(){
        try{
            cut.buscarPorIdYAspirante(aspiranteOpcion.getIdAspiranteOpcion(), null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(20)
    @Test
    public void testBuscarPorIdYAspiranteBothNull(){
        try{
            cut.buscarPorIdYAspirante(null, null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(21)
    @Test
    public void testBuscarPorIdYAspiranteEmNull(){
        cut.em = null;
        try{
            cut.buscarPorIdYAspirante(aspiranteOpcion.getIdAspiranteOpcion(), aspirante.getIdAspirante());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(22)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(23)
    @Test
    public void testBuscarPorRangoFirstNegativo(){
        try{
            cut.buscarPorRango(-1, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(24)
    @Test
    public void testBuscarPorRangoMaxNegativo(){
        try{
            cut.buscarPorRango(0, -1);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(25)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(aspiranteOpcion.getIdAspiranteOpcion());
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(26)
    @Test
    public void testBuscarPorIdParametroNull(){
        try{
            cut.buscarPorId(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(27)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try{
            cut.eliminar(aspiranteOpcion);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(28)
    @Test
    public void testEliminarParametroNull(){
        try{
            cut.eliminar(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(29)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try{
            cut.actualizar(aspiranteOpcion);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(30)
    @Test
    public void testActualizarParametroNull(){
        try{
            cut.actualizar(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(31)
    @Test
    public void testContarEmNull(){
        cut.em = null;
        try{
            cut.contar();
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

}
