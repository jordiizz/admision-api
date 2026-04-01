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
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.AreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.DistractorAreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.DistractorDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Distractor;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorArea;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.DistractorAreaPK;

@Path("distractor/{id_distractor}/area")
public class DistractorAreaResource implements Serializable {

    @Inject
    DistractorAreaDAO distractorAreaDAO;

    @Inject
    DistractorDAO distractorDAO;

    @Inject
    AreaDAO areaDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(
            @PathParam("id_distractor") UUID idDistractor,
            DistractorArea distractorArea,
            @Context UriInfo uriInfo) {
        if (distractorArea != null && idDistractor != null && distractorArea.getIdArea() != null
                && distractorArea.getIdArea().getIdArea() != null) {
            try {
                Distractor distractor = distractorDAO.buscarPorId(idDistractor);
                Area area = areaDAO.buscarPorId(distractorArea.getIdArea().getIdArea());
                if (distractor == null || area == null) {
                    String mensaje = distractor == null ? "Distractor no encontrado" : "Area no encontrada";
                    return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(), mensaje).build();
                }
                distractorArea.setIdDistractor(distractor);
                distractorArea.setIdArea(area);
                distractorAreaDAO.crear(distractorArea);

                UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                UUID areaId = distractorArea.getIdArea().getIdArea();
                if (areaId != null) {
                    uriBuilder.path(areaId.toString());
                }
                return Response.created(uriBuilder.build()).entity(distractorArea).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                        .build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                        "El recurso no puede ser nulo y se requiere idDistractor e idArea")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorRango(
            @PathParam("id_distractor") UUID idDistractor,
            @QueryParam("first") @DefaultValue("0") int firstResult,
            @QueryParam("max") @DefaultValue("50") int maxResults) {
        try {
            if (idDistractor != null && firstResult >= 0 && maxResults > 0 && maxResults <= 50) {
                List<DistractorArea> registros = distractorAreaDAO.buscarPorDistractorRango(idDistractor, firstResult, maxResults);
                Long total = distractorAreaDAO.contarPorDistractor(idDistractor);
                return Response.ok(registros)
                        .header(ResponseHeaders.TOTAL_RECORDS.toString(), total)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "Los parámetros 'first' debe ser >= 0 y 'max' debe ser > 0 y <= 50")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("{id_area}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(
            @PathParam("id_distractor") UUID idDistractor,
            @PathParam("id_area") UUID idArea) {
        if (idDistractor != null && idArea != null) {
            try {
                DistractorArea encontrado = distractorAreaDAO.buscarPorId(new DistractorAreaPK(idDistractor, idArea));
                if (encontrado != null) {
                    return Response.ok(encontrado).type(MediaType.APPLICATION_JSON).build();
                }
                return Response.status(Response.Status.NOT_FOUND)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                        .build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .header(ResponseHeaders.WRONG_PARAMETER.toString(), "idDistractor e idArea no pueden ser nulos")
                .build();
    }

    @PUT
    @Path("{id_area}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("id_distractor") UUID idDistractor,
            @PathParam("id_area") UUID idArea,
            DistractorArea distractorArea) {
        if (distractorArea != null && idDistractor != null && idArea != null
                && distractorArea.getIdArea() != null && distractorArea.getIdArea().getIdArea() != null) {
            try {
                if (!idArea.equals(distractorArea.getIdArea().getIdArea())) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .header(ResponseHeaders.WRONG_PARAMETER.toString(), "El idArea del body debe coincidir con el id_area del path")
                            .build();
                }
                DistractorArea existente = distractorAreaDAO.buscarPorId(new DistractorAreaPK(idDistractor, idArea));
                if (existente != null) {
                    distractorArea.setIdDistractor(new Distractor(idDistractor));
                    Area area = areaDAO.buscarPorId(distractorArea.getIdArea().getIdArea());
                    if (area == null) {
                        return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(), "Area no encontrada").build();
                    }
                    distractorArea.setIdArea(area);
                    distractorAreaDAO.actualizar(distractorArea);
                    return Response.ok(distractorArea).build();
                }
                return Response.status(Response.Status.NOT_FOUND)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                        .build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                        "El recurso no puede ser nulo y idDistractor e idArea no pueden ser nulos")
                .build();
    }

    @DELETE
    @Path("{id_area}")
    public Response eliminar(
            @PathParam("id_distractor") UUID idDistractor,
            @PathParam("id_area") UUID idArea) {
        if (idDistractor != null && idArea != null) {
            try {
                DistractorArea distractorArea = distractorAreaDAO.buscarPorId(new DistractorAreaPK(idDistractor, idArea));
                if (distractorArea != null) {
                    distractorAreaDAO.eliminar(distractorArea);
                    return Response.noContent().build();
                }
                return Response.status(Response.Status.NOT_FOUND)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                        .build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .header(ResponseHeaders.WRONG_PARAMETER.toString(), "idDistractor e idArea no pueden ser nulos")
                .build();
    }
}
