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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionExamenDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

@Path("prueba-jornada-aula-aspirante-opcion/{idPruebaJornadaAulaAspiranteOpcion}/examen")
public class PruebaJornadaAulaAspiranteOpcionExamenResource implements Serializable {

    @Inject
    PruebaJornadaAulaAspiranteOpcionExamenDAO pruebaJornadaAulaAspiranteOpcionExamenDAO;

    @Inject
    PruebaJornadaAulaAspiranteOpcionDAO pruebaJornadaAulaAspiranteOpcionDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPadre,
            PruebaJornadaAulaAspiranteOpcionExamen entity,
            @Context UriInfo uriInfo) {

        if (idPadre == null || entity == null || entity.getResultado() == null) {
            return Response.status(422)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(), "ID Padre, cuerpo o resultado faltantes")
                    .build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcion padre = pruebaJornadaAulaAspiranteOpcionDAO.buscarPorId(idPadre);
            if (padre == null) {
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Padre no encontrado").build();
            }

            entity.setIdPruebaJornadaAulaAspiranteOpcionExamen(UUID.randomUUID());
            entity.setIdPruebaJornadaAulaAspiranteOpcion(padre);
            pruebaJornadaAulaAspiranteOpcionExamenDAO.crear(entity);

            return Response.created(uriInfo.getAbsolutePathBuilder().path(entity.getIdPruebaJornadaAulaAspiranteOpcionExamen().toString()).build())
                    .entity(entity).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorRango(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPadre,
            @QueryParam("first") @DefaultValue("0") int first,
            @QueryParam("max") @DefaultValue("50") int max) {

        if (idPadre == null || first < 0 || max <= 0 || max > 50) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Parámetros inválidos").build();
        }

        try {
            List<PruebaJornadaAulaAspiranteOpcionExamen> registros = pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorPadreRango(idPadre, first, max);
            Long total = pruebaJornadaAulaAspiranteOpcionExamenDAO.contarPorPadre(idPadre);
            return Response.ok(registros).header(ResponseHeaders.TOTAL_RECORDS.toString(), total).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPadre,
            @PathParam("id") UUID id) {

        if (idPadre == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs requeridos").build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcionExamen encontrado = pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorIdYPadre(id, idPadre);
            return (encontrado != null) ? Response.ok(encontrado).build() 
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
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPadre,
            @PathParam("id") UUID id,
            PruebaJornadaAulaAspiranteOpcionExamen entity) {

        if (idPadre == null || entity == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Datos insuficientes para actualizar").build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcionExamen existente = pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorIdYPadre(id, idPadre);
            if (existente == null) {
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "No encontrado").build();
            }

            entity.setIdPruebaJornadaAulaAspiranteOpcionExamen(id);
            entity.setIdPruebaJornadaAulaAspiranteOpcion(existente.getIdPruebaJornadaAulaAspiranteOpcion());
            pruebaJornadaAulaAspiranteOpcionExamenDAO.actualizar(entity);
            return Response.ok(entity).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(
            @PathParam("idPruebaJornadaAulaAspiranteOpcion") UUID idPadre,
            @PathParam("id") UUID id) {

        if (idPadre == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs requeridos").build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcionExamen existente = pruebaJornadaAulaAspiranteOpcionExamenDAO.buscarPorIdYPadre(id, idPadre);
            if (existente == null) {
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "No encontrado").build();
            }
            pruebaJornadaAulaAspiranteOpcionExamenDAO.eliminar(existente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }
}