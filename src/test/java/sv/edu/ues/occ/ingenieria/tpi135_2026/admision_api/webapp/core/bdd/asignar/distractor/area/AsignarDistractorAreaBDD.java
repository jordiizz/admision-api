package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.asignar.distractor.area;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.AbstractBDD;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;

public class AsignarDistractorAreaBDD extends AbstractBDD {

	@Before
	public void setup() {
		initClient();
	}

	Distractor distractor;
	Area area;

	@Given("se tiene un servidor corriendo con la aplicación desplegada")
	public void se_tiene_un_servidor_corriendo_con_la_aplicacion_desplegada() {
		System.out.println("Iniciando contenedores de base de datos y servidor de aplicaciones");
		Assertions.assertTrue(postgres.isRunning());
		Assertions.assertTrue(liberty.isRunning());
	}

	@Given("que existe el distractor {string} con ID {string} en el catálogo")
	public void que_existe_el_distractor_con_id_en_el_catalogo(String valor, String id) {
		int esperado = 200;

		Response respuesta = target.path("distractor")
				.path(id)
				.request(MediaType.APPLICATION_JSON)
				.get();

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
		distractor = respuesta.readEntity(Distractor.class);
		Assertions.assertNotNull(distractor);
		Assertions.assertEquals(UUID.fromString(id), distractor.getIdDistractor());
		Assertions.assertEquals(valor, distractor.getValor());
		System.out.println("Distractor: " + distractor.getValor());
	}

	@Given("que existe el área {string} con ID {string}")
	public void que_existe_el_area_con_id(String nombre, String id) {
		int esperado = 200;

		Response respuesta = target.path("area")
				.path(id)
				.request(MediaType.APPLICATION_JSON)
				.get();

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
		area = respuesta.readEntity(Area.class);
		Assertions.assertNotNull(area);
		Assertions.assertEquals(UUID.fromString(id), area.getIdArea());
		Assertions.assertEquals(nombre, area.getNombre());
		System.out.println("Area: " + area.getNombre());
	}

	@When("asocio el distractor con ID {string} al área con ID {string}")
	public void asocio_el_distractor_con_id_al_area_con_id(String idDistractor, String idArea) {
		System.out.println("Asociando distractor a area");
		int esperado = 201;

		Response respuesta = target.path("distractor")
				.path(idDistractor)
				.path("area")
				.path(idArea)
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(null));

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
	}

	@Then("al consultar las áreas del distractor con ID {string}, verifico que está vinculado al área con ID {string}")
	public void al_consultar_las_areas_del_distractor_con_id_verifico_que_esta_vinculado_al_area_con_id(String idDistractor, String idArea) {
		System.out.println("Consultando asignacion de distractor y area por id");
		int esperado = 200;

		Response respuesta = target.path("distractor")
				.path(idDistractor)
				.path("area")
				.path(idArea)
				.request(MediaType.APPLICATION_JSON)
				.get();

		Assertions.assertNotNull(respuesta);
		Assertions.assertEquals(esperado, respuesta.getStatus());
		DistractorArea registro = respuesta.readEntity(DistractorArea.class);
		Assertions.assertNotNull(registro);
		Assertions.assertEquals(UUID.fromString(idDistractor), registro.getIdDistractor().getIdDistractor());
		Assertions.assertEquals(UUID.fromString(idArea), registro.getIdArea().getIdArea());
	}
}
