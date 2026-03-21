/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author everg
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AreaDAOIT extends AbstractIntengrationDAOTest{

    AreaDAO cut;

    @BeforeEach // una antes de cada prueba
    public void antesdecadaprueba(){
        cut = new AreaDAO();
        cut.em = em;
    }

    @Order(1)
    @Test
    public void testCount() {
        System.out.println("count");
        Long resultado = cut.contar();
        assertTrue(resultado == 0);
        System.out.println("Resultado: " + resultado);
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());

        // Asignamos un EntityManager defectuoso
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());

        //fail("The test case is a propotype");
    }

    @Order(2)
    @Test
    public void testCrear() {
        System.out.println("crear");
        Area nuevo = new Area(UUID.randomUUID());
        nuevo.setNombre("registro prueba 1");
        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
        cut.em = null;
        assertThrows(IllegalArgumentException.class, () -> {cut.crear(null);});
        assertThrows(IllegalStateException.class, () -> {cut.crear(new Area(UUID.randomUUID()));});
        //fail("The test case is a propotype");
    }

    @Order(3)
    @Test
    public void buscarPorId(){
        System.out.println("buscarPorId");

        // Preparar el entorno
        UUID idBuscado = UUID.randomUUID();
        Area nuevo = new Area(idBuscado);
        nuevo.setNombre("Area a buscar");

        // Realizar la transacción
        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        // Buscamos
        Area encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(encontrado.getIdArea(), idBuscado);
        assertEquals(encontrado.getNombre(), nuevo.getNombre());
        System.out.println("Id: " + idBuscado);

        cut.em = null;
        assertThrows(IllegalArgumentException.class, () -> {cut.buscarPorId(null);});
        assertThrows(IllegalStateException.class, () -> {cut.buscarPorId(idBuscado);});

        //fail("La prueba falló");
    }

}
