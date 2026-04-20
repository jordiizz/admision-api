package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.actualizar.pregunta;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("bdd.actualizar.pregunta")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.bdd.actualizar.pregunta")
public class ActualizarPreguntaRunner {
}
