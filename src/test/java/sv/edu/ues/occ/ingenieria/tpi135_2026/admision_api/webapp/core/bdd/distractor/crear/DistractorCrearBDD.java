package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.distractor.crear;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;

import java.util.UUID;

public class DistractorCrearBDD extends AbstractBDD {

    @Before
    public void setup(){
        initClient();
    }

    Distractor distractor;

    @Given("se tiene un servidor corriendo con la aplicación desplegada.")
    public void se_tiene_un_servidor_corriendo_con_la_aplicación_desplegada() {
        System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
        Assertions.assertTrue(postgres.isRunning());
        Assertions.assertTrue(liberty.isRunning());
    }
    @When("envío la petición para crear un distractor con el valor {string}")
    public void envío_la_petición_para_crear_un_distractor_con_el_valor(String valor) {
        System.out.println("Creando un distractor con el valor " + valor);
        int esperado = 201;
        distractor = new Distractor();
        distractor.setValor(valor);
        distractor.setActivo(true);

        Response respuesta = target.path("distractor")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(distractor));

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        UUID id = UUID.fromString(respuesta.getHeaderString("Location").split("distractor/")[1]);
        Assertions.assertNotNull(id);
        System.out.println("ID: "+ id);
        distractor.setIdDistractor(id);
    }
    @Then("al consultar el distractor recién creado su valor es {string}")
    public void al_consultar_el_distractor_recién_creado_su_valor_es(String valor) {
        System.out.println("Consultado distractor con el valor: " + valor);
        int esperado = 200;

        Response respuesta = target.path("distractor")
                .path(distractor.getIdDistractor().toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertNotNull(respuesta);
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Distractor encontrado = respuesta.readEntity(Distractor.class);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(distractor.getValor(), encontrado.getValor());
        Assertions.assertEquals(distractor.getIdDistractor(), encontrado.getIdDistractor());
        System.out.println("Distractor: "+ distractor.getIdDistractor() + " Valor: "+distractor.getValor());
    }


}
