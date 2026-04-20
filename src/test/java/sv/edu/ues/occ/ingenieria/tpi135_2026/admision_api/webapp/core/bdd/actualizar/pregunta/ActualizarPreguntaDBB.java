package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.actualizar.pregunta;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;

import java.util.UUID;

public class ActualizarPreguntaDBB extends AbstractBDD {

	@Before
	public void setup(){
		initClient();
	}

	Pregunta pregunta;

	@Given("se tiene un servidor corriendo con la aplicación desplegada")
	public void se_tiene_un_servidor_corriendo_con_la_aplicacion_desplegada() {
		System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
		Assertions.assertTrue(postgres.isRunning());
		Assertions.assertTrue(liberty.isRunning());
	}

	@Given("que existe la pregunta {string} con ID {string} en el sistema")
	public void que_existe_la_pregunta_con_id_en_el_sistema(String valorPregunta, String idPregunta) {
		System.out.println("Buscando pregunta existente para actualizar");

		int esperado = 200;

		Response respuesta = target.path("pregunta")
				.path(idPregunta)
				.request(MediaType.APPLICATION_JSON)
				.get();

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
		pregunta = respuesta.readEntity(Pregunta.class);
		Assertions.assertNotNull(pregunta);
		Assertions.assertEquals(UUID.fromString(idPregunta), pregunta.getIdPregunta());
		Assertions.assertEquals(valorPregunta, pregunta.getValor());
		System.out.println("Pregunta: " + pregunta.getValor());
	}

	@When("actualizo la pregunta con ID {string} con el nuevo enunciado {string}")
	public void actualizo_la_pregunta_con_id_con_el_nuevo_enunciado(String idPregunta, String nuevoEnunciado) {
		System.out.println("Actualizando enunciado de pregunta");

		int esperado = 200;
		Pregunta actualizada = new Pregunta();
		actualizada.setValor(nuevoEnunciado);
		actualizada.setActivo(pregunta.getActivo());
		actualizada.setImagenUrl(pregunta.getImagenUrl());

		Response respuesta = target.path("pregunta")
				.path(idPregunta)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.json(actualizada));

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
	}

	@Then("al consultar la pregunta con ID {string} verifico que el enunciado ahora es {string}")
	public void al_consultar_la_pregunta_con_id_verifico_que_el_enunciado_ahora_es(String idPregunta, String nuevoEnunciado) {
		System.out.println("Consultando pregunta actualizada");

		int esperado = 200;

		Response respuesta = target.path("pregunta")
				.path(idPregunta)
				.request(MediaType.APPLICATION_JSON)
				.get();

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
		Pregunta encontrada = respuesta.readEntity(Pregunta.class);
		Assertions.assertNotNull(encontrada);
		Assertions.assertEquals(UUID.fromString(idPregunta), encontrada.getIdPregunta());
		Assertions.assertEquals(nuevoEnunciado, encontrada.getValor());
		System.out.println("Pregunta actualizada: " + encontrada.getValor());
	}
}
