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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionExamenDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

/**
 * REST Resource para gestionar los exámenes asociados a un registro de
 * PruebaJornadaAulaAspiranteOpcion.
 *
 * <p>Ruta base:
 * prueba-jornada-aula-aspirante-opcion/{idPruebaJornadaAulaAspiranteOpcion}/examen</p>
 */
@Path("prueba-jornada-aula-aspirante-opcion/{idPruebaJornadaAulaAspiranteOpcion}/examen")
public class PruebaJornadaAulaAspiranteOpcionExamenResource implements Serializable {

    @Inject
    PruebaJornadaAulaAspiranteOpcionExamenDAO pruebaJornadaAulaAspiranteOpcionExamenDAO;

    @Inject
    PruebaJornadaAulaAspiranteOpcionDAO pruebaJornadaAulaAspiranteOpcionDAO;

    private Response validarIdPadre(UUID idPruebaJornadaAulaAspiranteOpcion) {
        if (idPruebaJornadaAulaAspiranteOpcion == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "idPruebaJornadaAulaAspiranteOpcion no puede ser nulo")
                    .build();
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPruebaJornadaAulaAspiranteOpcion,
            PruebaJornadaAulaAspiranteOpcionExamen entity,
            @Context UriInfo uriInfo) {
        Response validacion = validarIdPadre(idPruebaJornadaAulaAspiranteOpcion);
        if (validacion != null) return validacion;
        if (entity == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "El recurso no puede ser nulo")
                    .build();
        }
        if (entity.getResultado() == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "resultado es requerido")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcion padre =
                    pruebaJornadaAulaAspiranteOpcionDAO.buscarPorId(idPruebaJornadaAulaAspiranteOpcion);
            if (padre == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(),
                                "PruebaJornadaAulaAspiranteOpcion no encontrada")
                        .build();
            }
            entity.setIdPruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID());
            entity.setIdPruebaJornadaAulaAspiranteOpcion(padre);
            pruebaJornadaAulaAspiranteOpcionExamenDAO.crear(entity);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(entity.getIdPruebaJornadaAulaAspiranteOpcionExamen().toString());
            return Response.created(uriBuilder.build()).entity(entity).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(500)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorRango(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPruebaJornadaAulaAspiranteOpcion,
            @QueryParam("first") @DefaultValue("0") int firstResult,
            @QueryParam("max") @DefaultValue("50") int maxResults) {
        Response validacion = validarIdPadre(idPruebaJornadaAulaAspiranteOpcion);
        if (validacion != null) return validacion;
        if (firstResult < 0 || maxResults <= 0 || maxResults > 50) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "Los parámetros 'first' debe ser >= 0 y 'max' debe ser > 0 y <= 50")
                    .build();
        }
        try {
            List<PruebaJornadaAulaAspiranteOpcionExamen> registros =
                    pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorPadreRango(
                            idPruebaJornadaAulaAspiranteOpcion, firstResult, maxResults);
            Long total = pruebaJornadaAulaAspiranteOpcionExamenDAO
                    .contarPorPadre(idPruebaJornadaAulaAspiranteOpcion);
            return Response.ok(registros)
                    .header(ResponseHeaders.TOTAL_RECORDS.toString(), total)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(500)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPruebaJornadaAulaAspiranteOpcion,
            @PathParam("id") UUID id) {
        Response validacion = validarIdPadre(idPruebaJornadaAulaAspiranteOpcion);
        if (validacion != null) return validacion;
        if (id == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "id no puede ser nulo")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcionExamen encontrado =
                    pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorIdYPadre(
                            id, idPruebaJornadaAulaAspiranteOpcion);
            if (encontrado != null) {
                return Response.ok(encontrado).type(MediaType.APPLICATION_JSON).build();
            }
            return Response.status(404)
                    .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                    .build();
        } catch (Exception e) {
            return Response.status(500)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPruebaJornadaAulaAspiranteOpcion,
            PruebaJornadaAulaAspiranteOpcionExamen entity) {
        Response validacion = validarIdPadre(idPruebaJornadaAulaAspiranteOpcion);
        if (validacion != null) return validacion;
        if (entity == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "El recurso no puede ser nulo")
                    .build();
        }
        if (entity.getIdPruebaJornadaAulaAspiranteOpcionExamen() == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "El ID debe enviarse en el body")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcionExamen existente =
                    pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorIdYPadre(
                            entity.getIdPruebaJornadaAulaAspiranteOpcionExamen(),
                            idPruebaJornadaAulaAspiranteOpcion);
            if (existente == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            }
            entity.setIdPruebaJornadaAulaAspiranteOpcion(
                    existente.getIdPruebaJornadaAulaAspiranteOpcion());
            pruebaJornadaAulaAspiranteOpcionExamenDAO.actualizar(entity);
            return Response.ok(entity).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(500)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPruebaJornadaAulaAspiranteOpcion,
            @PathParam("id") UUID id) {
        Response validacion = validarIdPadre(idPruebaJornadaAulaAspiranteOpcion);
        if (validacion != null) return validacion;
        if (id == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "id no puede ser nulo")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcionExamen existente =
                    pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorIdYPadre(
                            id, idPruebaJornadaAulaAspiranteOpcion);
            if (existente == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            }
            pruebaJornadaAulaAspiranteOpcionExamenDAO.eliminar(existente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(500)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }
}
