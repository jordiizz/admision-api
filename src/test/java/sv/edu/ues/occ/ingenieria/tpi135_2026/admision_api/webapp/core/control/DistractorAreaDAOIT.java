package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorAreaPK;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DistractorAreaDAOIT extends AbstractIntengrationDAOTest {

    AreaDAO areaDAO = new AreaDAO();
    DistractorDAO distractorDAO = new DistractorDAO();

    DistractorAreaDAO cut = new DistractorAreaDAO();

    Area area = new Area(UUID.randomUUID());
    Area area2 = new Area(UUID.randomUUID());
    Distractor distractor = new Distractor(UUID.randomUUID());
    Distractor distractor2 = new Distractor(UUID.randomUUID());

    DistractorAreaPK pk = new DistractorAreaPK(distractor.getIdDistractor(), area.getIdArea());
    DistractorArea distractorArea = new DistractorArea(distractor, area);
    DistractorAreaPK pk2 = new DistractorAreaPK(distractor2.getIdDistractor(), area.getIdArea());
    DistractorArea distractorArea2 = new DistractorArea(distractor2, area);
    DistractorAreaPK pk3 = new DistractorAreaPK(distractor2.getIdDistractor(), area2.getIdArea());
    DistractorArea distractorArea3 = new DistractorArea(distractor2, area2);
    DistractorAreaPK pk4 =  new DistractorAreaPK(distractor.getIdDistractor(), area2.getIdArea());
    DistractorArea distractorArea4 = new DistractorArea(distractor, area2);

    @BeforeEach
    public void setUp() {
        cut.em = em;
        areaDAO.em = em;
        distractorDAO.em = em;

        area.setNombre("MATEMATICAS");
        area.setDescripcion("Ciencia de los números");
        area.setActivo(true);

        area2.setNombre("LENGUAJE");
        area2.setDescripcion("Ciencia del lenguaje");
        area2.setActivo(true);

        distractor.setValor("4");
        distractor.setActivo(true);

        distractor2.setValor("5");
        distractor2.setActivo(true);
    }

    public void crearContexto() {
        areaDAO.crear(area);
        areaDAO.crear(area2);
        distractorDAO.crear(distractor);
        distractorDAO.crear(distractor2);
    }

    @Order(1)
    @Test
    public void testCrear() {
        em.getTransaction().begin();
        crearContexto();
        em.getTransaction().commit();

        em.getTransaction().begin();
        Long registros = cut.contar();
        cut.crear(distractorArea);
        cut.crear(distractorArea2);
        cut.crear(distractorArea3);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();

        assertTrue(registrosDespues > registros);
    }

    @Order(2)
    @Test
    public void testCrearEmNull(){
        cut.em = null;
        try{
            cut.crear(distractorArea4);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(3)
    @Test
    public void testCrearParametrosInvalidos(){
        try{
            cut.crear(null);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(4)
    @Test
    public void testBuscarPorId() {
        em.getTransaction().begin();
        DistractorArea resultado = cut.buscarPorId(pk);
        DistractorArea resultado2 = cut.buscarPorId(pk2);
        em.getTransaction().commit();

        assertTrue(resultado != null);
        assertTrue(resultado2 != null);
        assertEquals(resultado, distractorArea);
        assertEquals(resultado2, distractorArea2);
    }

    @Order(5)
    @Test
    public void testBuscarPorRango() {
        int fisrt = 0;
        int max = 10;
        List<DistractorArea> resultados = cut.buscarPorRango(fisrt, max);
        assertTrue(resultados.size() >= 2);
    }

    @Order(6)
    @Test
    public void testBuscarPorDistractorRango() {
        int first = 0;
        int max = 10;
        List<DistractorArea> resultados = cut.buscarPorDistractorRango(distractor2.getIdDistractor(), first, max);
        assertFalse(resultados.isEmpty());
        assertTrue(resultados.size() >= 2);
    }

    @Order(7)
    @Test
    public void testBuscarPorDistractorRangoIdDistractorNull() {
        try {
            cut.buscarPorDistractorRango(null, 0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(8)
    @Test
    public void testBuscarPorIdDistractorRangoFirstWrong() {
        try {
            cut.buscarPorDistractorRango(distractor2.getIdDistractor(), -50, 10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(9)
    @Test
    public void testBuscarPorIdDistractorRangoMaxWrong() {
        try {
            cut.buscarPorDistractorRango(distractor2.getIdDistractor(), 0, -10);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(10)
    @Test
    public void testBuscarPorIdDistractorRangoEmNull() {
        cut.em = null;
        try {
            cut.buscarPorDistractorRango(distractor2.getIdDistractor(), 0, 10);
        } catch (Exception ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(11)
    @Test
    public void testContarPorDistractor() {
        Long registros = cut.contarPorDistractor(distractor2.getIdDistractor());
        assertTrue(registros >= 2);
    }

    @Order(12)
    @Test
    public void testContarPorDistractorEmNull(){
        cut.em = null;
        try{
            cut.contarPorDistractor(distractor2.getIdDistractor());
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(13)
    @Test
    public void testContarPorDistractorParametrosInvalidos(){
        try{
            cut.contarPorDistractor(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(14)
    @Test
    public void testBuscarPorRangoEmNull(){
        cut.em = null;
        try{
            cut.buscarPorRango(0, 10);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(15)
    @Test
    public void testBuscarPorRangoFirstNegativo(){
        try{
            cut.buscarPorRango(-1, 10);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(16)
    @Test
    public void testBuscarPorRangoMaxNegativo(){
        try{
            cut.buscarPorRango(0, -1);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(17)
    @Test
    public void testBuscarPorIdEmNull(){
        cut.em = null;
        try{
            cut.buscarPorId(pk);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(18)
    @Test
    public void testBuscarPorIdParametroNull(){
        try{
            cut.buscarPorId(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(19)
    @Test
    public void testEliminarEmNull(){
        cut.em = null;
        try{
            cut.eliminar(distractorArea);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(20)
    @Test
    public void testEliminarParametroNull(){
        try{
            cut.eliminar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(21)
    @Test
    public void testActualizarExistente(){
        em.getTransaction().begin();
        DistractorArea resultado = cut.buscarPorId(pk);
        em.getTransaction().commit();

        assertTrue(resultado != null);

        em.getTransaction().begin();
        DistractorArea actualizado = cut.actualizar(resultado);
        em.getTransaction().commit();

        assertEquals(resultado.getIdDistractor().getIdDistractor(), actualizado.getIdDistractor().getIdDistractor());
    }

    @Order(22)
    @Test
    public void testActualizarEmNull(){
        cut.em = null;
        try{
            cut.actualizar(distractorArea);
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(23)
    @Test
    public void testActualizarParametroNull(){
        try{
            cut.actualizar(null);
        }catch (Exception ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
        }
    }

    @Order(24)
    @Test
    public void testContarEmNull(){
        cut.em = null;
        try{
            cut.contar();
        }catch (Exception ex){
            assertEquals(IllegalStateException.class, ex.getClass());
        }
    }

    @Order(25)
    @Test
    public void testContar(){
        Long registros = cut.contar();
        assertTrue(registros >= 3);
    }

    @Order(26)
    @Test
    public void testEliminar(){
        em.getTransaction().begin();
        cut.crear(distractorArea4);
        Long registros = cut.contar();
        cut.eliminar(distractorArea4);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(registrosDespues < registros);

    }

    @Order(27)
    @Test
    public void testEliminarNoContained(){
        em.getTransaction().begin();
        DistractorArea distractorAreaNoContained = new DistractorArea(distractorArea3.getIdDistractor(), distractorArea3.getIdArea());
        Long registros = cut.contar();
        cut.eliminar(distractorAreaNoContained);
        Long registrosDespues = cut.contar();
        em.getTransaction().commit();
        assertTrue(registrosDespues < registros);
    }
}
