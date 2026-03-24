package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.EntityManager;
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
public class AspiranteDAOIT extends AbstractIntengrationDAOTest{

    AspiranteDAO cut; // Class under test

    @BeforeEach
    public void setup() {
        cut = new AspiranteDAO();
        cut.em = em;
    }

    @Order(1)
    @Test
    public void testCrearExitoso(){
        System.out.println("Crear aspirante exitoso");
        Aspirante nuevo = new Aspirante(UUID.randomUUID());
        nuevo.setNombres("Aspirante 1");
        nuevo.setApellidos("Aspirante 1");
        nuevo.setCorreo("aspirante@gmail.com");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setFechaCreacion(OffsetDateTime.now());

        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Long resultado = cut.contar();
        assertEquals(1, resultado);
        System.out.println("Resultado: " + resultado);
    }

    @Order(2)
    @Test
    public void testCrearEntidadNula() {
        System.out.println("Crear Aspirante entidad null");
        // Dejamos que falle por la validación de la entidad nula
        assertThrows(IllegalArgumentException.class, () -> { cut.crear(null); });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear Aspirante Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> { cut.crear(new Aspirante(UUID.randomUUID())); });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar aspirante exitoso");
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar aspirante Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar aspirante Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId aspirante exitoso");
        UUID idBuscado = UUID.randomUUID();
        Aspirante nuevo = new Aspirante(idBuscado);
        nuevo.setNombres("Aspirante 1");
        nuevo.setApellidos("Aspirante 1");
        nuevo.setCorreo("aspirante2@gmail.com");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setFechaCreacion(OffsetDateTime.now());

        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Aspirante encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(idBuscado, encontrado.getIdAspirante());
        assertEquals(nuevo.getNombres(), encontrado.getNombres());
        assertEquals(nuevo.getApellidos(), encontrado.getApellidos());
        assertEquals(nuevo.getCorreo(), encontrado.getCorreo());
        assertEquals(nuevo.getFechaNacimiento(), encontrado.getFechaNacimiento());
        assertEquals(nuevo.getFechaCreacion(), encontrado.getFechaCreacion());
        System.out.println("Id: " + idBuscado);
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId aspirante null");
        assertThrows(IllegalArgumentException.class, () -> { cut.buscarPorId(null); });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId aspirante em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> { cut.buscarPorId(UUID.randomUUID()); });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar Aspirante entidad no guardada");
        UUID idEliminado = UUID.randomUUID();
        Aspirante eliminado = new Aspirante(idEliminado);
        eliminado.setNombres("Aspirante 1");
        eliminado.setApellidos("Aspirante 1");
        eliminado.setCorreo("aspirante3@gmail.com");
        eliminado.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        eliminado.setFechaCreacion(OffsetDateTime.now());

        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar Aspirante exitoso");
        UUID idEliminado = UUID.randomUUID();
        Aspirante eliminado = new Aspirante(idEliminado);
        eliminado.setNombres("Aspirante 1");
        eliminado.setApellidos("Aspirante 1");
        eliminado.setCorreo("aspirante4@gmail.com");
        eliminado.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        eliminado.setFechaCreacion(OffsetDateTime.now());

        // Guardamos
        cut.em.getTransaction().begin();
        cut.crear(eliminado);
        cut.em.getTransaction().commit();

        // eliminamos
        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(12)
    @Test
    public void testEliminarEntidadNula() {
        System.out.println("eliminar aspirante entidad nula");
        assertThrows(IllegalArgumentException.class, () -> { cut.eliminar(null); });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar aspirante em nulo");
        cut.em = null;
        Aspirante eliminado = new Aspirante(UUID.randomUUID());
        assertThrows(IllegalStateException.class, () -> { cut.eliminar(eliminado); });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso(){
        System.out.println("actualizar aspirante exitoso");
        Aspirante nuevo = new Aspirante(UUID.randomUUID());
        nuevo.setNombres("Aspirante 1");
        nuevo.setApellidos("Aspirante 1");
        nuevo.setCorreo("aspirante5@gmail.com");
        nuevo.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        nuevo.setFechaCreacion(OffsetDateTime.now());


        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        // Modificar la entidad
        nuevo.setNombres("Actualizado");
        cut.em.getTransaction().begin();
        Aspirante actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getNombres(), actualizado.getNombres());
        System.out.println("Nombre actualizado: " + actualizado.getNombres());
        // fail("Esta prueba falló");
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar aspirante entidad nula");
        assertThrows(IllegalArgumentException.class, () -> { cut.actualizar(null); });
        // fail("Esta prueba falló");
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar aspirante em nulo");
        // Hacemos la entity manager null
        cut.em = null;
        Aspirante actualizado = new Aspirante(UUID.randomUUID());

        assertThrows(IllegalStateException.class, () -> { cut.actualizar(actualizado); });
        // fail("Esta prueba falló");
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso(){
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<Aspirante> aspirantes = cut.buscarPorRango(0, 50);
        assertNotNull(aspirantes);
        assertEquals(esperado, aspirantes.size());
        // fail("Esta prueba falló");
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango aspirante parametros no validos");
        assertThrows(IllegalArgumentException.class, () -> {cut.buscarPorRango(0, -1);});
        assertThrows(IllegalArgumentException.class, () -> {cut.buscarPorRango(-1, 50);});
        //fail("Esta prueba falló");
    }

    @Order(19)
    @Test
    public void testBuscarPorRangoEmNull() {
        System.out.println("buscarPorRango aspirante parametros no validos");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {cut.buscarPorRango(0, 50);});
        //fail("Esta prueba falló");
    }

}
