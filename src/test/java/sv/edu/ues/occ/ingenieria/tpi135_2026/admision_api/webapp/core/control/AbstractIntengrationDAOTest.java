package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public abstract class AbstractIntengrationDAOTest {

    static EntityManager em;
    static EntityManagerFactory emf;

    @Container
    static GenericContainer postgres = new PostgreSQLContainer("postgres:16")
            .withDatabaseName("tpi135")
            .withInitScript("database.sql")
            .withPassword("abc123")
            .withUsername("postgres")
            .withExposedPorts(5432)
            ;

    @BeforeAll
    public static void inicializar() {
        Integer puertoPostgresql = postgres.getMappedPort(5432);
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://localhost:%d/tpi135?stringtype=unspecified", puertoPostgresql));
        emf = Persistence.createEntityManagerFactory("AdmisionPUIT", propiedades);
        em = emf.createEntityManager();
        assertTrue(postgres.isRunning());
    }

    // Después de finalizar las puebas, cerrrar los recursos.
    @AfterAll
    public static void limpiar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
