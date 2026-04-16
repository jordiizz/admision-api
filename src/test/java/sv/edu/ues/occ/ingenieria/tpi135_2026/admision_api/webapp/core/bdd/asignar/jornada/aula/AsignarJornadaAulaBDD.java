package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.asignar.jornada.aula;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AsignarJornadaAulaBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    Jornada jornada;

    @Given("se tiene un servidor corriendo con la aplicación desplegada")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }
    @When("registro una nueva jornada llamada {string} con inicio {string} y fin {string}")
    public void registro_una_nueva_jornada_llamada_con_inicio_y_fin(String nombre, String inicio, String fin) {
        System.out.println("Creando una jornada");
        int esperado = 201;

        jornada = new Jornada();
        jornada.setNombre(nombre);
        jornada.setFechaInicio(OffsetDateTime.parse(inicio));
        jornada.setFechaFin(OffsetDateTime.parse(fin));

        Response respuesta = target.path("jornada")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jornada));
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("jornada/")[1]);
        Assertions.assertNotNull(id);
        jornada.setIdJornada(id);
        System.out.println(id);
    }
    @When("asigno el aula {string} a la jornada")
    public void asigno_el_aula_a_la_jornada(String aula) {
        System.out.println("Asignando el aula: "+ aula);

        int esperado = 201;
        Response response = target.path("jornada")
                .path(jornada.getIdJornada().toString())
                .path("aula")
                .path(aula)
                .request(MediaType.APPLICATION_JSON)
                .post(null);

        assertNotNull(response);
        assertEquals(esperado, response.getStatus());
        JornadaAula jornadaAula = response.readEntity(JornadaAula.class);
        assertNotNull(jornadaAula);
        Assertions.assertEquals(jornadaAula.getIdJornada().getIdJornada(), jornada.getIdJornada());
        Assertions.assertEquals(aula, jornadaAula.getIdAula());
        System.out.println(jornadaAula+ " idJornada: "+ jornadaAula.getIdJornada());
    }
    @When("asigno una segunda aula {string} a la jornada")
    public void asigno_una_segunda_aula_a_la_jornada(String aula) {
        System.out.println("Asignando el aula: "+ aula);

        int esperado = 201;
        Response response = target.path("jornada")
                .path(jornada.getIdJornada().toString())
                .path("aula")
                .path(aula)
                .request(MediaType.APPLICATION_JSON)
                .post(null);

        assertNotNull(response);
        assertEquals(esperado, response.getStatus());
        JornadaAula jornadaAula = response.readEntity(JornadaAula.class);
        assertNotNull(jornadaAula);
        Assertions.assertEquals(jornadaAula.getIdJornada().getIdJornada(), jornada.getIdJornada());
        Assertions.assertEquals(aula, jornadaAula.getIdAula());
        System.out.println(jornadaAula+ " idJornada: "+ jornadaAula.getIdJornada());
    }
    @Then("al verificar que las Aulas fueron correctamente asignadas")
    public void al_verificar_que_las_aulas_fueron_correctamente_asignadas() {
        System.out.println("Verificando que las aulas han sido asignadas");

        int esperado = 200;

        Response response = target
                .path("jornada")
                .path(jornada.getIdJornada().toString())
                .path("aula")
                .request().get();
        assertNotNull(response);
        assertEquals(esperado, response.getStatus());
        List<JornadaAula> registros = response.readEntity(new GenericType<List<JornadaAula>>() {});
        Assertions.assertNotNull(registros);
        Assertions.assertTrue(registros.size()>=2);
        for (JornadaAula jornadaAula : registros) {
            System.out.println(jornadaAula);
            System.out.println(jornadaAula.getIdJornada());
            Assertions.assertEquals(jornadaAula.getIdJornada().getIdJornada(), jornada.getIdJornada());
        }
    }


}
