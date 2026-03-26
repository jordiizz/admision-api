package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DistractorDAOIT extends AbstractIntengrationDAOTest {

    DistractorDAO cut; // Class under test

    @BeforeEach
    public void setup() {
        cut = new DistractorDAO();
        cut.em = em;
    }

    @Order(1)
    @Test
    public void testCrearExitoso() {
        System.out.println("Crear distractor exitoso");
        Distractor nuevo = new Distractor(UUID.randomUUID());
        nuevo.setValor("Distractor 1");

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
        System.out.println("Crear Distractor entidad null");
        // Dejamos que falle por la validación de la entidad nula
        assertThrows(IllegalArgumentException.class, () -> { cut.crear(null); });
    }

    @Order(3)
    @Test
    public void testCrearEmNulo() {
        System.out.println("Crear Distractor Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> { cut.crear(new Distractor(UUID.randomUUID())); });
    }

    @Order(4)
    @Test
    public void testCountExitoso() {
        System.out.println("Contar distractor exitoso");
        Long resultado = cut.contar();
        assertEquals(resultado, 1);
        System.out.println("Resultado: " + resultado);
    }

    @Order(5)
    @Test
    public void testCountEmNulo() {
        System.out.println("Contar distractor Entity manager null");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(6)
    @Test
    public void testCountEmCerrado() {
        System.out.println("Contar distractor Entity manager cerrado");
        EntityManager emCerrado = emf.createEntityManager();
        emCerrado.close();
        cut.em = emCerrado;
        assertThrows(IllegalStateException.class, () -> cut.contar());
    }

    @Order(7)
    @Test
    public void testBuscarPorIdExitoso() {
        System.out.println("BuscarPorId distractor exitoso");
        UUID idBuscado = UUID.randomUUID();
        Distractor nuevo = new Distractor(idBuscado);
        nuevo.setValor("Distractor 1");

        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        Distractor encontrado = cut.buscarPorId(idBuscado);
        assertNotNull(encontrado);
        assertEquals(idBuscado, encontrado.getIdDistractor());
        assertEquals(nuevo.getValor(), encontrado.getValor());
        System.out.println("Id: " + idBuscado);
    }

    @Order(8)
    @Test
    public void testBuscarPorIdNulo() {
        System.out.println("BuscarPorId distractor null");
        assertThrows(IllegalArgumentException.class, () -> { cut.buscarPorId(null); });
    }

    @Order(9)
    @Test
    public void testBuscarPorIdEmNulo() {
        System.out.println("buscarPorId distractor em nulo");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> { cut.buscarPorId(UUID.randomUUID()); });
    }

    @Order(10)
    @Test
    public void testEliminarNoExistente() {
        System.out.println("Eliminar Distractor entidad no guardada");
        UUID idEliminado = UUID.randomUUID();
        Distractor eliminado = new Distractor(idEliminado);
        eliminado.setValor("Distractor 1");

        cut.em.getTransaction().begin();
        cut.eliminar(eliminado);
        cut.em.getTransaction().commit();

        assertNull(cut.buscarPorId(idEliminado));
    }

    @Order(11)
    @Test
    public void testEliminarExitoso() {
        System.out.println("Eliminar Distractor exitoso");
        UUID idEliminado = UUID.randomUUID();
        Distractor eliminado = new Distractor(idEliminado);
        eliminado.setValor("Distractor 1");

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
        System.out.println("eliminar distractor entidad nula");
        assertThrows(IllegalArgumentException.class, () -> { cut.eliminar(null); });
    }

    @Order(13)
    @Test
    public void testEliminarEmNulo() {
        System.out.println("eliminar distractor em nulo");
        cut.em = null;
        Distractor eliminado = new Distractor(UUID.randomUUID());
        assertThrows(IllegalStateException.class, () -> { cut.eliminar(eliminado); });
    }

    @Order(14)
    @Test
    public void testActualizarExitoso(){
        System.out.println("actualizar distractor exitoso");
        Distractor nuevo = new Distractor(UUID.randomUUID());
        nuevo.setValor("Distractor 1");

        cut.em.getTransaction().begin();
        cut.crear(nuevo);
        cut.em.getTransaction().commit();

        // Modificar la entidad
        nuevo.setValor("Actualizado");
        cut.em.getTransaction().begin();
        Distractor actualizado = cut.actualizar(nuevo);
        cut.em.getTransaction().commit();

        assertNotNull(actualizado);
        assertEquals(nuevo.getValor(), actualizado.getValor());
        System.out.println("Nombre actualizado: " + actualizado.getValor());
    }

    @Order(15)
    @Test
    public void testActualizarEntidadNula() {
        System.out.println("actualizar distractor entidad nula");
        assertThrows(IllegalArgumentException.class, () -> { cut.actualizar(null); });
    }

    @Order(16)
    @Test
    public void testActualizarEmNulo() {
        System.out.println("actualizar distractor em nulo");
        // Hacemos la entity manager null
        cut.em = null;
        Distractor actualizado = new Distractor(UUID.randomUUID());

        assertThrows(IllegalStateException.class, () -> { cut.actualizar(actualizado); });
    }

    @Order(17)
    @Test
    public void testBuscarPorRangoExtitoso(){
        System.out.println("buscarPorRango - extitoso");
        Long esperado = cut.contar();

        List<Distractor> distractores = cut.buscarPorRango(0, 50);
        assertNotNull(distractores);
        assertEquals(esperado, distractores.size());
    }

    @Order(18)
    @Test
    public void testBuscarPorRangoPametrosNoValidos() {
        System.out.println("buscarPorRango distractor parametros no validos");
        assertThrows(IllegalArgumentException.class, () -> {cut.buscarPorRango(0, -1);});
        assertThrows(IllegalArgumentException.class, () -> {cut.buscarPorRango(-1, 50);});
    }

    @Order(19)
    @Test
    public void testBuscarPorRangoEmNull() {
        System.out.println("buscarPorRango distractor parametros no validos");
        cut.em = null;
        assertThrows(IllegalStateException.class, () -> {cut.buscarPorRango(0, 50);});
    }
}