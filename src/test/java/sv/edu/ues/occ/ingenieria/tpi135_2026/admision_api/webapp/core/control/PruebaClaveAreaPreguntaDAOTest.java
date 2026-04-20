package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;

public class PruebaClaveAreaPreguntaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.constructorTest");
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        assertNotNull(cut);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.getEntityManagerTest");
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaClaveAreaPreguntaDAOTest.getEntityManagerTest - finalizado");
    }

    @Test
    public void testBuscarPorClaveYAreaExitoso() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaExitoso");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        UUID idArea = UUID.randomUUID();
        List<PruebaClaveAreaPregunta> listaEsperada = List.of(
            new PruebaClaveAreaPregunta(),
            new PruebaClaveAreaPregunta()
        );

        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPregunta> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findByClaveAndArea", PruebaClaveAreaPregunta.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(listaEsperada);

        // Act
        List<PruebaClaveAreaPregunta> resultado = cut.buscarPorClaveYArea(idPruebaClave, idArea);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(listaEsperada, resultado);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaExitoso - finalizado");
    }

    @Test
    public void testBuscarPorClaveYAreaVacio() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaVacio");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        UUID idArea = UUID.randomUUID();

        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPregunta> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findByClaveAndArea", PruebaClaveAreaPregunta.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        // Act
        List<PruebaClaveAreaPregunta> resultado = cut.buscarPorClaveYArea(idPruebaClave, idArea);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaVacio - finalizado");
    }

    @Test
    public void testBuscarPorClaveYAreaIdPruebaClaveNulo() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaIdPruebaClaveNulo");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idArea = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorClaveYArea(null, idArea));

        assertNotNull(ex);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaIdPruebaClaveNulo - finalizado");
    }

    @Test
    public void testBuscarPorClaveYAreaIdAreaNulo() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaIdAreaNulo");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorClaveYArea(idPruebaClave, null));

        assertNotNull(ex);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaIdAreaNulo - finalizado");
    }

    @Test
    public void testBuscarPorClaveYAreaAmbosParametrosNulos() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaAmbosParametrosNulos");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.buscarPorClaveYArea(null, null));

        assertNotNull(ex);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaAmbosParametrosNulos - finalizado");
    }

    @Test
    public void testBuscarPorClaveYAreaExcepcionEnQuery() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaExcepcionEnQuery");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        UUID idArea = UUID.randomUUID();

        @SuppressWarnings("unchecked")
        TypedQuery<PruebaClaveAreaPregunta> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findByClaveAndArea", PruebaClaveAreaPregunta.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idArea", idArea)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenThrow(new RuntimeException("Error en BD"));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> cut.buscarPorClaveYArea(idPruebaClave, idArea));

        assertNotNull(ex);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testBuscarPorClaveYAreaExcepcionEnQuery - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaExitosoDentroDelLimite() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaExitosoDentroDelLimite");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        BigDecimal porcentajeExistente = new BigDecimal("30.00");
        BigDecimal porcentajeNuevo = new BigDecimal("20.00");
        BigDecimal puntajeMaximo = new BigDecimal("100.00");

        PruebaClave pruebaClave = Mockito.mock(PruebaClave.class);
        Mockito.when(pruebaClave.getIdPruebaClave()).thenReturn(idPruebaClave);

        Prueba prueba = Mockito.mock(Prueba.class);
        Mockito.when(prueba.getPuntajeMaximo()).thenReturn(puntajeMaximo);
        Mockito.when(pruebaClave.getIdPrueba()).thenReturn(prueba);

        PruebaClaveAreaPregunta pruebaClaveAreaPregunta = Mockito.mock(PruebaClaveAreaPregunta.class);
        Mockito.when(pruebaClaveAreaPregunta.getPorcentaje()).thenReturn(porcentajeNuevo);

        @SuppressWarnings("unchecked")
        TypedQuery<BigDecimal> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findTotalPorcentajeByPruebaClave", BigDecimal.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(porcentajeExistente);

        // Act
        boolean resultado = cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta);

        // Assert
        assertTrue(resultado); // 30 + 20 = 50, que es <= 100
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaExitosoDentroDelLimite - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaExitosoFueraDelLimite() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaExitosoFueraDelLimite");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        BigDecimal porcentajeExistente = new BigDecimal("80.00");
        BigDecimal porcentajeNuevo = new BigDecimal("30.00");
        BigDecimal puntajeMaximo = new BigDecimal("100.00");

        PruebaClave pruebaClave = Mockito.mock(PruebaClave.class);
        Mockito.when(pruebaClave.getIdPruebaClave()).thenReturn(idPruebaClave);

        Prueba prueba = Mockito.mock(Prueba.class);
        Mockito.when(prueba.getPuntajeMaximo()).thenReturn(puntajeMaximo);
        Mockito.when(pruebaClave.getIdPrueba()).thenReturn(prueba);

        PruebaClaveAreaPregunta pruebaClaveAreaPregunta = Mockito.mock(PruebaClaveAreaPregunta.class);
        Mockito.when(pruebaClaveAreaPregunta.getPorcentaje()).thenReturn(porcentajeNuevo);

        @SuppressWarnings("unchecked")
        TypedQuery<BigDecimal> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findTotalPorcentajeByPruebaClave", BigDecimal.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(porcentajeExistente);

        // Act
        boolean resultado = cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta);

        // Assert
        assertFalse(resultado); // 80 + 30 = 110, que es > 100
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaExitosoFueraDelLimite - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaTotalActualNull() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaTotalActualNull");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        BigDecimal porcentajeNuevo = new BigDecimal("50.00");
        BigDecimal puntajeMaximo = new BigDecimal("100.00");

        PruebaClave pruebaClave = Mockito.mock(PruebaClave.class);
        Mockito.when(pruebaClave.getIdPruebaClave()).thenReturn(idPruebaClave);

        Prueba prueba = Mockito.mock(Prueba.class);
        Mockito.when(prueba.getPuntajeMaximo()).thenReturn(puntajeMaximo);
        Mockito.when(pruebaClave.getIdPrueba()).thenReturn(prueba);

        PruebaClaveAreaPregunta pruebaClaveAreaPregunta = Mockito.mock(PruebaClaveAreaPregunta.class);
        Mockito.when(pruebaClaveAreaPregunta.getPorcentaje()).thenReturn(porcentajeNuevo);

        @SuppressWarnings("unchecked")
        TypedQuery<BigDecimal> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findTotalPorcentajeByPruebaClave", BigDecimal.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(null); // totalActual es null

        // Act
        boolean resultado = cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta);

        // Assert
        assertTrue(resultado); // 0 (ZERO) + 50 = 50, que es <= 100
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaTotalActualNull - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaPruebaClaveNull() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaPruebaClaveNull");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        PruebaClaveAreaPregunta pruebaClaveAreaPregunta = Mockito.mock(PruebaClaveAreaPregunta.class);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.validarPorcentajePrueba(null, pruebaClaveAreaPregunta));

        assertEquals("pruebaClave y pruebaClaveAreaPregunta no pueden ser null", ex.getMessage());
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaPruebaClaveNull - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaAreaPreguntaNull() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaAreaPreguntaNull");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        PruebaClave pruebaClave = Mockito.mock(PruebaClave.class);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.validarPorcentajePrueba(pruebaClave, null));

        assertEquals("pruebaClave y pruebaClaveAreaPregunta no pueden ser null", ex.getMessage());
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaAreaPreguntaNull - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaBothNull() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaBothNull");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.validarPorcentajePrueba(null, null));

        assertEquals("pruebaClave y pruebaClaveAreaPregunta no pueden ser null", ex.getMessage());
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaBothNull - finalizado");
    }

    @Test
    public void testValidarPorcentajePruebaExcepcionEnQuery() {
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaExcepcionEnQuery");

        // Arrange
        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idPruebaClave = UUID.randomUUID();
        PruebaClave pruebaClave = Mockito.mock(PruebaClave.class);
        Mockito.when(pruebaClave.getIdPruebaClave()).thenReturn(idPruebaClave);

        PruebaClaveAreaPregunta pruebaClaveAreaPregunta = Mockito.mock(PruebaClaveAreaPregunta.class);

        @SuppressWarnings("unchecked")
        TypedQuery<BigDecimal> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("PruebaClaveAreaPregunta.findTotalPorcentajeByPruebaClave", BigDecimal.class))
            .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idPruebaClave", idPruebaClave)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenThrow(new RuntimeException("Error en BD"));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta));

        assertNotNull(ex);
        System.out.println("PruebaClaveAreaPreguntaDAOTest.testValidarPorcentajePruebaExcepcionEnQuery - finalizado");
    }

}