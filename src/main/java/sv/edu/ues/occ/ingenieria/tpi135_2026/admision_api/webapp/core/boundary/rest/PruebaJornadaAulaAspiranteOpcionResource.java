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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.AspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaAulaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;

/**
 * REST Resource para gestionar los aspirantes-opcion asignados a un aula
 * dentro de una prueba-jornada.
 *
 * <p>Ruta base: prueba-jornada/{idPruebaJornada}/jornada-aula/{idJornadaAula}/aspirante-opcion</p>
 * <p>Valida los tres padres requeridos: PruebaJornada (ruta), JornadaAula (ruta),
 * AspiranteOpcion (cuerpo de la solicitud).</p>
 */
@Path("prueba-jornada/{idPruebaJornada}/jornada-aula/{idJornadaAula}/aspirante-opcion")
public class PruebaJornadaAulaAspiranteOpcionResource implements Serializable {

    @Inject
    PruebaJornadaAulaAspiranteOpcionDAO pruebaJornadaAulaAspiranteOpcionDAO;

    @Inject
    PruebaJornadaDAO pruebaJornadaDAO;

    @Inject
    JornadaAulaDAO jornadaAulaDAO;

    @Inject
    AspiranteOpcionDAO aspiranteOpcionDAO;

    private Response validarIds(UUID idPruebaJornada, UUID idJornadaAula) {
        if (idPruebaJornada == null || idJornadaAula == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "idPruebaJornada e idJornadaAula no pueden ser nulos")
                    .build();
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(
            @PathParam("idPruebaJornada") UUID idPruebaJornada,
            @PathParam("idJornadaAula") UUID idJornadaAula,
            PruebaJornadaAulaAspiranteOpcion entity,
            @Context UriInfo uriInfo) {
        Response validacion = validarIds(idPruebaJornada, idJornadaAula);
        if (validacion != null) return validacion;
        if (entity == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "El recurso no puede ser nulo")
                    .build();
        }
        if (entity.getIdAspiranteOpcion() == null
                || entity.getIdAspiranteOpcion().getIdAspiranteOpcion() == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "idAspiranteOpcion es requerido en el body")
                    .build();
        }
        try {
            PruebaJornada pruebaJornada = pruebaJornadaDAO.buscarPorId(idPruebaJornada);
            if (pruebaJornada == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "PruebaJornada no encontrada")
                        .build();
            }
            JornadaAula jornadaAula = jornadaAulaDAO.buscarPorId(idJornadaAula);
            if (jornadaAula == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "JornadaAula no encontrada")
                        .build();
            }
            if (jornadaAula.getIdJornada() == null || pruebaJornada.getIdJornada() == null ||
                !jornadaAula.getIdJornada().getIdJornada().equals(pruebaJornada.getIdJornada().getIdJornada())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                                "La JornadaAula no pertenece a la misma jornada que la PruebaJornada indicada")
                        .build();
            }
            AspiranteOpcion aspiranteOpcion = aspiranteOpcionDAO
                    .buscarPorId(entity.getIdAspiranteOpcion().getIdAspiranteOpcion());
            if (aspiranteOpcion == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "AspiranteOpcion no encontrada")
                        .build();
            }
            entity.setIdPruebaJornadaAulaAspiranteOpcion(UUID.randomUUID());
            entity.setIdPruebaJornada(pruebaJornada);
            entity.setIdJornadaAula(jornadaAula);
            entity.setIdAspiranteOpcion(aspiranteOpcion);
            pruebaJornadaAulaAspiranteOpcionDAO.crear(entity);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(entity.getIdPruebaJornadaAulaAspiranteOpcion().toString());
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
            @PathParam("idPruebaJornada") UUID idPruebaJornada,
            @PathParam("idJornadaAula") UUID idJornadaAula,
            @QueryParam("first") @DefaultValue("0") int firstResult,
            @QueryParam("max") @DefaultValue("50") int maxResults) {
        Response validacion = validarIds(idPruebaJornada, idJornadaAula);
        if (validacion != null) return validacion;
        if (firstResult < 0 || maxResults <= 0 || maxResults > 50) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "Los parámetros 'first' debe ser >= 0 y 'max' debe ser > 0 y <= 50")
                    .build();
        }
        try {
            List<PruebaJornadaAulaAspiranteOpcion> registros =
                    pruebaJornadaAulaAspiranteOpcionDAO.buscarPorPruebaJornadaYJornadaAulaRango(
                            idPruebaJornada, idJornadaAula, firstResult, maxResults);
            Long total = pruebaJornadaAulaAspiranteOpcionDAO
                    .contarPorPruebaJornadaYJornadaAula(idPruebaJornada, idJornadaAula);
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
            @PathParam("idPruebaJornada") UUID idPruebaJornada,
            @PathParam("idJornadaAula") UUID idJornadaAula,
            @PathParam("id") UUID id) {
        Response validacion = validarIds(idPruebaJornada, idJornadaAula);
        if (validacion != null) return validacion;
        if (id == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "id no puede ser nulo")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcion encontrado =
                    pruebaJornadaAulaAspiranteOpcionDAO.buscarPorIdYPruebaJornadaYJornadaAula(
                            id, idPruebaJornada, idJornadaAula);
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
            @PathParam("idPruebaJornada") UUID idPruebaJornada,
            @PathParam("idJornadaAula") UUID idJornadaAula,
            PruebaJornadaAulaAspiranteOpcion entity) {
        Response validacion = validarIds(idPruebaJornada, idJornadaAula);
        if (validacion != null) return validacion;
        if (entity == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "El recurso no puede ser nulo")
                    .build();
        }
        if (entity.getIdPruebaJornadaAulaAspiranteOpcion() == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "El ID debe enviarse en el body")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcion existente =
                    pruebaJornadaAulaAspiranteOpcionDAO.buscarPorIdYPruebaJornadaYJornadaAula(
                            entity.getIdPruebaJornadaAulaAspiranteOpcion(), idPruebaJornada, idJornadaAula);
            if (existente == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            }
            if (entity.getIdAspiranteOpcion() != null
                    && entity.getIdAspiranteOpcion().getIdAspiranteOpcion() != null) {
                AspiranteOpcion aspiranteOpcion = aspiranteOpcionDAO
                        .buscarPorId(entity.getIdAspiranteOpcion().getIdAspiranteOpcion());
                if (aspiranteOpcion == null) {
                    return Response.status(404)
                            .header(ResponseHeaders.NOT_FOUND.toString(), "AspiranteOpcion no encontrada")
                            .build();
                }
                entity.setIdAspiranteOpcion(aspiranteOpcion);
            } else {
                entity.setIdAspiranteOpcion(existente.getIdAspiranteOpcion());
            }
            entity.setIdPruebaJornada(existente.getIdPruebaJornada());
            entity.setIdJornadaAula(existente.getIdJornadaAula());
            pruebaJornadaAulaAspiranteOpcionDAO.actualizar(entity);
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
            @PathParam("idPruebaJornada") UUID idPruebaJornada,
            @PathParam("idJornadaAula") UUID idJornadaAula,
            @PathParam("id") UUID id) {
        Response validacion = validarIds(idPruebaJornada, idJornadaAula);
        if (validacion != null) return validacion;
        if (id == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(),
                            "id no puede ser nulo")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcion existente =
                    pruebaJornadaAulaAspiranteOpcionDAO.buscarPorIdYPruebaJornadaYJornadaAula(
                            id, idPruebaJornada, idJornadaAula);
            if (existente == null) {
                return Response.status(404)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado")
                        .build();
            }
            pruebaJornadaAulaAspiranteOpcionDAO.eliminar(existente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(500)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }
}
