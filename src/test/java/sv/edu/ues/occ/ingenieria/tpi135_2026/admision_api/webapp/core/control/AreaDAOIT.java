/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author everg
 */
@Testcontainers
public class AreaDAOIT {

    @Container
    GenericContainer postgres = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("tpi135")
            .withInitScript("database.sql")
            .withPassword("abc123")
            .withUsername("postgres")
            .withExposedPorts(5432)
            ;
    @Test
    public void testCount() {
        System.out.println("count");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdmisionPUIT");
        EntityManager em = emf.createEntityManager();
        AreaDAO cut = new AreaDAO();
        cut.em = em;
        assertTrue(postgres.isRunning());
        Integer puertoPostgresql = postgres.getMappedPort(5432);
        Long resultado = cut.contar();
        assertTrue(resultado == 0);
        fail("The test case is a propotype");
    }
    
}
