package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.nio.file.Paths;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AreaResourceIT {

    Network red = Network.newNetwork();

    MountableFile war = MountableFile.forHostPath(Paths.get("target/admision-api.war").toAbsolutePath());

    @Container
    GenericContainer postgres = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("tpi135")
            .withInitScript("database.sql")
            .withPassword("abc123")
            .withUsername("postgres")
            .withNetwork(red)
            .withNetworkAliases("db")
            .withExposedPorts(5432)
            ;

    @Container
    GenericContainer liberty = new GenericContainer("openliberty-pg:10.24.0.0.8")
            .withEnv("PGSERVER", "db")
            .withEnv("PGPORT", "5432")
            .withEnv("PGDBNAME", "tpi135")
            .withEnv("PGUSER", "postgres")
            .withEnv("PGPASSWORD", "abc123")
            .dependsOn(postgres) // que no arranque si no ha arrancado la base de datos
            .withNetwork(red)
            .withCopyFileToContainer(war, "/home/usuario/wlp/usr/servers/app/dropins/nuevoingreso.war")
            .withExposedPorts(9080)
            ;
}
