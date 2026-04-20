package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;

public class PruebaDAOTest {

    @Test
    public void constructorTest() {
        System.out.println("PruebaDAOTest.constructorTest");
        PruebaDAO cut = new PruebaDAO();
        assertNotNull(cut);
        System.out.println("PruebaDAOTest.constructorTest - finalizado");
    }

    @Test
    public void getEntityManagerTest() {
        System.out.println("PruebaDAOTest.getEntityManagerTest");
        PruebaDAO cut = new PruebaDAO();
        assertNull(cut.getEntityManager());
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;
        assertEquals(mockEM, cut.getEntityManager());
        System.out.println("PruebaDAOTest.getEntityManagerTest - finalizado");
    }



    @Test
    public void testFindByIdAspiranteExitoso() {
        System.out.println("PruebaDAOTest.testFindByIdAspiranteExitoso");

        // Arrange
        PruebaDAO cut = new PruebaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idAspirante = UUID.randomUUID();
        List<Prueba> pruebasEsperadas = List.of(
            new Prueba(UUID.randomUUID()),
            new Prueba(UUID.randomUUID())
        );

        @SuppressWarnings("unchecked")
        TypedQuery<Prueba> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("Prueba.findByIdAspirante", Prueba.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspirante", idAspirante)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(pruebasEsperadas);

        // Act
        List<Prueba> resultado = cut.findByIdAspirante(idAspirante);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pruebasEsperadas, resultado);
        System.out.println("PruebaDAOTest.testFindByIdAspiranteExitoso - finalizado");
    }

    @Test
    public void testFindByIdAspiranteVacio() {
        System.out.println("PruebaDAOTest.testFindByIdAspiranteVacio");

        // Arrange
        PruebaDAO cut = new PruebaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idAspirante = UUID.randomUUID();

        @SuppressWarnings("unchecked")
        TypedQuery<Prueba> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("Prueba.findByIdAspirante", Prueba.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspirante", idAspirante)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Prueba> resultado = cut.findByIdAspirante(idAspirante);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        System.out.println("PruebaDAOTest.testFindByIdAspiranteVacio - finalizado");
    }

    @Test
    public void testFindByIdAspiranteIdNulo() {
        System.out.println("PruebaDAOTest.testFindByIdAspiranteIdNulo");

        // Arrange
        PruebaDAO cut = new PruebaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cut.findByIdAspirante(null));

        assertEquals("El id del aspirante no puede ser nulo", ex.getMessage());
        System.out.println("PruebaDAOTest.testFindByIdAspiranteIdNulo - finalizado");
    }

    @Test
    public void testFindByIdAspiranteExceptionEnGetResultList() {
        System.out.println("PruebaDAOTest.testFindByIdAspiranteExceptionEnGetResultList");

        // Arrange
        PruebaDAO cut = new PruebaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idAspirante = UUID.randomUUID();
        
        @SuppressWarnings("unchecked")
        TypedQuery<Prueba> mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockEM.createNamedQuery("Prueba.findByIdAspirante", Prueba.class)).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter("idAspirante", idAspirante)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenThrow(new RuntimeException("Error en BD"));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> cut.findByIdAspirante(idAspirante));

        assertNotNull(ex);
        System.out.println("PruebaDAOTest.testFindByIdAspiranteExceptionEnGetResultList - finalizado");
    }

    @Test
    public void testFindByIdAspiranteExceptionEnCreateNamedQuery() {
        System.out.println("PruebaDAOTest.testFindByIdAspiranteExceptionEnCreateNamedQuery");

        // Arrange
        PruebaDAO cut = new PruebaDAO();
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        cut.em = mockEM;

        UUID idAspirante = UUID.randomUUID();
        
        Mockito.when(mockEM.createNamedQuery("Prueba.findByIdAspirante", Prueba.class))
            .thenThrow(new RuntimeException("NamedQuery no encontrada"));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> cut.findByIdAspirante(idAspirante));

        assertNotNull(ex);
        System.out.println("PruebaDAOTest.testFindByIdAspiranteExceptionEnCreateNamedQuery - finalizado");
    }

}