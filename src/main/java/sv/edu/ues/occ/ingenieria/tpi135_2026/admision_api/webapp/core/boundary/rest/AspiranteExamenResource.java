package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionExamenDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

@Path("aspirante/{id_aspirante}/prueba")
public class AspiranteExamenResource implements Serializable {

    @Inject
    PruebaJornadaAulaAspiranteOpcionExamenDAO pruebaJornadaAulaAspiranteOpcionExamenDAO;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id_prueba}/examen")
    public Response actualizarResultado(
            @PathParam("id_aspirante") UUID idAspirante,
            @PathParam("id_prueba") UUID idPrueba,
            PruebaJornadaAulaAspiranteOpcionExamen entity) {

        if (idAspirante == null || idPrueba == null || entity == null || entity.getResultado() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(), "Los IDs de aspirante y prueba, y resultado son requeridos")
                    .build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcionExamen examen = pruebaJornadaAulaAspiranteOpcionExamenDAO
                    .obtenerResultadoExamenPorAspiranteYPrueba(idAspirante, idPrueba);

            if (examen == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Examen no encontrado para el aspirante y prueba especificados")
                        .build();
            }

            examen.setResultado(entity.getResultado());
            examen.setFechaResultado(OffsetDateTime.now());
            pruebaJornadaAulaAspiranteOpcionExamenDAO.actualizar(examen);
            return Response.ok(examen).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }
}
