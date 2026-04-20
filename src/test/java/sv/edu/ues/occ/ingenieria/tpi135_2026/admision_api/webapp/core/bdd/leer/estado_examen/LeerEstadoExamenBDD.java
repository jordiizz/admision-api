package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.leer.estado_examen;

import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.ExamenResultadosEnum;


import static org.junit.jupiter.api.Assertions.*;

public class LeerEstadoExamenBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    ExamenResultadosEnum estado;
    String idAspirante;

    @Given("se tiene una aplicacion desplegada en un servidor")
    public void se_tiene_una_aplicacion_desplegada_en_un_servidor() {
        assertTrue(postgres.isRunning());
        assertTrue(liberty.isRunning());
    }

    @When("la aspirante {string} consulta las pruebas que ha realizado")
    public void la_aspirante_consulta_las_pruebas_que_ha_realizado(String idAspirante) {
        this.idAspirante = idAspirante;
        Response respuesta = target.path("aspirante").path(idAspirante)
                .path("pruebas")
                .request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, respuesta.getStatus());
    }

    @When("desea consultar su resultado para la prueba con id {string}")
    public void desea_consultar_su_resultado_para_la_prueba_con_id(String idPrueba) {

        Response respuesta = target.path("aspirante").path(idAspirante)
                .path("prueba").path(idPrueba)
                .request(MediaType.APPLICATION_JSON).get();

        estado = respuesta.readEntity(ExamenResultadosEnum.class);

        assertEquals(200, respuesta.getStatus());



    }
    @Then("el sistema devuelve que su estado es {string}")
    public void el_sistema_devuelve_que_su_estado_es(String estadoDeseado) {
        assertEquals(estadoDeseado, estado.name());

    }


}
