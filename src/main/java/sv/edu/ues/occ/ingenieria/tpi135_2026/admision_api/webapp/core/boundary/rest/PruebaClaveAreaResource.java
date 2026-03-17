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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveArea;

@Path("prueba-clave/{idPruebaClave}/area")
public class PruebaClaveAreaResource implements Serializable {

    @Inject
    PruebaClaveAreaDAO pruebaClaveAreaDAO;

    @Inject
    PruebaClaveDAO pruebaClaveDAO;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crear(
            @PathParam("idPruebaClave") UUID idPruebaClave,
            PruebaClaveArea pruebaClaveArea,
            @Context UriInfo uriInfo) {
        if (pruebaClaveArea != null && idPruebaClave != null) {
            try {
                PruebaClave pruebaClave = pruebaClaveDAO.buscarPorId(idPruebaClave);
                if (pruebaClave == null) {
                    return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "PruebaClave no encontrada").build();
                }
                pruebaClaveArea.setIdPruebaClaveArea(UUID.randomUUID());
                pruebaClaveArea.setIdPruebaClave(pruebaClave);
                pruebaClaveAreaDAO.crear(pruebaClaveArea);
                UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                uriBuilder.path(pruebaClaveArea.getIdPruebaClaveArea().toString());
                return Response.created(uriBuilder.build()).entity(pruebaClaveArea).build();
            } catch (IllegalArgumentException | IllegalStateException e) {
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El recurso no puede ser nulo y idPruebaClave no puede ser nulo").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response buscarPorRango(
            @PathParam("idPruebaClave") UUID idPruebaClave,
            @QueryParam("first") @DefaultValue("0") int firstResult,
            @QueryParam("max") @DefaultValue("50") int maxResults) {
        try {
            if (idPruebaClave != null && firstResult >= 0 && maxResults > 0 && maxResults <= 50) {
                List<PruebaClaveArea> registros = pruebaClaveAreaDAO.buscarPorPruebaClaveRango(idPruebaClave, firstResult, maxResults);
                Long total = pruebaClaveAreaDAO.contarPorPruebaClave(idPruebaClave);
                return Response.ok(registros)
                        .header(ResponseHeaders.TOTAL_RECORDS.toString(), total)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Los parámetros 'first' debe ser >= 0 y 'max' debe ser > 0 y <= 50").build();
            }
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(
            @PathParam("idPruebaClave") UUID idPruebaClave,
            @PathParam("id") UUID id) {
        if (id != null && idPruebaClave != null) {
            try {
                PruebaClaveArea encontrado = pruebaClaveAreaDAO.buscarPorIdYPruebaClave(id, idPruebaClave);
                if (encontrado != null) {
                    return Response.ok(encontrado).type(MediaType.APPLICATION_JSON).build();
                }
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            } catch (Exception e) {
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "idPruebaClave e id no pueden ser nulos").build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("idPruebaClave") UUID idPruebaClave,
            @PathParam("id") UUID id,
            PruebaClaveArea pruebaClaveArea) {
        if (pruebaClaveArea != null && idPruebaClave != null && id != null) {
            try {
                PruebaClaveArea existente = pruebaClaveAreaDAO.buscarPorIdYPruebaClave(id, idPruebaClave);
                if (existente != null) {
                    pruebaClaveArea.setIdPruebaClaveArea(id);
                    pruebaClaveArea.setIdPruebaClave(existente.getIdPruebaClave());
                    pruebaClaveAreaDAO.actualizar(pruebaClaveArea);
                    return Response.ok(pruebaClaveArea).build();
                }
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            } catch (IllegalArgumentException | IllegalStateException e) {
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El recurso no puede ser nulo y idPruebaClave no puede ser nulo").build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(
            @PathParam("idPruebaClave") UUID idPruebaClave,
            @PathParam("id") UUID id) {
        if (id != null && idPruebaClave != null) {
            try {
                PruebaClaveArea pruebaClaveArea = pruebaClaveAreaDAO.buscarPorIdYPruebaClave(id, idPruebaClave);
                if (pruebaClaveArea != null) {
                    pruebaClaveAreaDAO.eliminar(pruebaClaveArea);
                    return Response.noContent().build();
                }
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            } catch (Exception e) {
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "idPruebaClave e id no pueden ser nulos").build();
    }
}
