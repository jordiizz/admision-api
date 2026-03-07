/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;

/**
 *
 * @author everg
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    public AreaDAOIT() {}

    @BeforeAll // Una vez para todas las pruebas
    public void inicicializar(){

    }

    @BeforeEach // una antes de cada prueba
    public void antesdecadaprueba(){

    }

    @Order(1)
    @Test
    public void testCount() {
        System.out.println("count");
        assertTrue(postgres.isRunning());
        Integer puertoPostgresql = postgres.getMappedPort(5432);

        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://localhost:%d/tpi135", puertoPostgresql));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdmisionPUIT", propiedades);
        EntityManager em = emf.createEntityManager();
        AreaDAO cut = new AreaDAO();
        cut.em = em;

        assertTrue(postgres.isRunning());
        Long resultado = cut.contar();
        assertTrue(resultado == 0);
        System.out.println("Resultado: " + resultado);
        //fail("The test case is a propotype");
    }

    @Order(2)
    @Test
    public void testCrear() {
        System.out.println("crear");
        Area nuevo = new Area(UUID.randomUUID());
        nuevo.setNombre("registro prueba 1");

        assertTrue(postgres.isRunning());
        Integer puertoPostgresql = postgres.getMappedPort(5432);
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://localhost:%d/tpi135", puertoPostgresql));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdmisionPUIT", propiedades);
        EntityManager em = emf.createEntityManager();

        AreaDAO cut = new AreaDAO();
        cut.em = em;
        cut.em.getTransaction().begin();

        // nuevo.setIdAreaPadre(cut.buscarPorRango(0, 1).getFirst());
        cut.crear(nuevo);

        cut.em.getTransaction().commit();

        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
        //fail("The test case is a propotype");
    }
}
