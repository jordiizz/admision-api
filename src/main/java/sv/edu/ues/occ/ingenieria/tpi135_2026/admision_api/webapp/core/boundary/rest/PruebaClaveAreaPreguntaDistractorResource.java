package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.DistractorDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaPreguntaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaPreguntaDistractorDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaDistractor;

@Path("prueba-clave-area-pregunta/{idPruebaClaveAreaPregunta}/distractor")
public class PruebaClaveAreaPreguntaDistractorResource implements Serializable {

    @Inject
    PruebaClaveAreaPreguntaDistractorDAO pruebaClaveAreaPreguntaDistractorDAO;

    @Inject
    PruebaClaveAreaPreguntaDAO pruebaClaveAreaPreguntaDAO;

    @Inject
    DistractorDAO distractorDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(
            @PathParam("idPruebaClaveAreaPregunta") UUID idPadre,
            PruebaClaveAreaPreguntaDistractor entity,
            @Context UriInfo uriInfo) {

        if (idPadre == null || entity == null || entity.getIdDistractor() == null
            || entity.getIdDistractor().getIdDistractor() == null) {
            return Response.status(422)
                .header(ResponseHeaders.WRONG_PARAMETER.toString(), "ID padre, cuerpo o distractor faltantes")
                .build();
        }

        try {
            PruebaClaveAreaPregunta padre = pruebaClaveAreaPreguntaDAO.buscarPorId(idPadre);
            Distractor distractor = distractorDAO.buscarPorId(entity.getIdDistractor().getIdDistractor());
            if (padre == null || distractor == null) {
                String message = (padre == null) ? "Padre no encontrado" : "Distractor no encontrado";
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), message).build();
            }

            entity.setIdPruebaClaveAreaPreguntaDistractor(UUID.randomUUID());
            entity.setIdPruebaClaveAreaPregunta(padre);
            entity.setIdDistractor(distractor);
            pruebaClaveAreaPreguntaDistractorDAO.crear(entity);

            return Response.created(uriInfo.getAbsolutePathBuilder()
                            .path(entity.getIdPruebaClaveAreaPreguntaDistractor().toString()).build())
                            .entity(entity)
                            .build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorRango(
            @PathParam("idPruebaClaveAreaPregunta") UUID idPadre,
            @QueryParam("first") @DefaultValue("0") int first,
            @QueryParam("max") @DefaultValue("50") int max) {

        if (idPadre == null || first < 0 || max <= 0 || max > 50) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Parámetros inválidos").build();
        }

        try {
            List<PruebaClaveAreaPreguntaDistractor> registros =
                    pruebaClaveAreaPreguntaDistractorDAO.buscarPorPadreRango(idPadre, first, max);
            Long total = pruebaClaveAreaPreguntaDistractorDAO.contarPorPadre(idPadre);
            return Response.ok(registros).header(ResponseHeaders.TOTAL_RECORDS.toString(), total).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(
            @PathParam("idPruebaClaveAreaPregunta") UUID idPadre,
            @PathParam("id") UUID id) {

        if (idPadre == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs requeridos").build();
        }

        try {
            PruebaClaveAreaPreguntaDistractor encontrado =
                    pruebaClaveAreaPreguntaDistractorDAO.buscarPorIdYPadre(id, idPadre);
            return (encontrado != null)
                    ? Response.ok(encontrado).build()
                    : Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "No encontrado").build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("idPruebaClaveAreaPregunta") UUID idPadre,
            @PathParam("id") UUID id,
            PruebaClaveAreaPreguntaDistractor entity) {

        if (idPadre == null || id == null || entity == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Datos insuficientes para actualizar").build();
        }

        try {
            PruebaClaveAreaPreguntaDistractor existente =
                    pruebaClaveAreaPreguntaDistractorDAO.buscarPorIdYPadre(id, idPadre);
            if (existente == null) {
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "No encontrado").build();
            }

            Distractor distractor = existente.getIdDistractor();
            if (entity.getIdDistractor() != null && entity.getIdDistractor().getIdDistractor() != null) {
                distractor = distractorDAO.buscarPorId(entity.getIdDistractor().getIdDistractor());
                if (distractor == null) {
                    return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Distractor no encontrado").build();
                }
            }

            entity.setIdDistractor(distractor);
            entity.setIdPruebaClaveAreaPreguntaDistractor(id);
            entity.setIdPruebaClaveAreaPregunta(existente.getIdPruebaClaveAreaPregunta());

            pruebaClaveAreaPreguntaDistractorDAO.actualizar(entity);
            return Response.ok(entity).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(
            @PathParam("idPruebaClaveAreaPregunta") UUID idPadre,
            @PathParam("id") UUID id) {

        if (idPadre == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs requeridos").build();
        }

        try {
            PruebaClaveAreaPreguntaDistractor existente =
                    pruebaClaveAreaPreguntaDistractorDAO.buscarPorIdYPadre(id, idPadre);
            if (existente == null) {
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "No encontrado").build();
            }

            pruebaClaveAreaPreguntaDistractorDAO.eliminar(existente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }
}
