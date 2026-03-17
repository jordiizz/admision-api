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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.AspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaAulaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaAulaAspiranteOpcionDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaJornadaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.AspiranteOpcion;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcion;

@Path("prueba-jornada/{idPruebaJornada}/jornada-aula/{idJornadaAula}/aspirante-opcion")
public class PruebaJornadaAulaAspiranteOpcionResource implements Serializable {

    @Inject PruebaJornadaAulaAspiranteOpcionDAO pruebaJornadaAulaAspiranteOpcionDAO;
    @Inject PruebaJornadaDAO pruebaJornadaDAO;
    @Inject JornadaAulaDAO jornadaAulaDAO;
    @Inject AspiranteOpcionDAO aspiranteOpcionDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(
            @PathParam("idPruebaJornada") UUID idPrueba,
            @PathParam("idJornadaAula") UUID idAula,
            PruebaJornadaAulaAspiranteOpcion entity,
            @Context UriInfo uriInfo) {

        if (idPrueba == null || idAula == null || entity == null || entity.getIdAspiranteOpcion() == null || entity.getIdAspiranteOpcion().getIdAspiranteOpcion() == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs de ruta o AspiranteOpcion faltantes").build();
        }

        try {
            PruebaJornada prueba = pruebaJornadaDAO.buscarPorId(idPrueba);
            JornadaAula aula = jornadaAulaDAO.buscarPorId(idAula);
            AspiranteOpcion aspirante = aspiranteOpcionDAO.buscarPorId(entity.getIdAspiranteOpcion().getIdAspiranteOpcion());

            if (prueba == null || aula == null || aspirante == null) {
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Dependencia no encontrada").build();
            }

            // Validación de pertenencia a la misma jornada
            if (aula.getIdJornada() == null || prueba.getIdJornada() == null || 
                !aula.getIdJornada().getIdJornada().equals(prueba.getIdJornada().getIdJornada())) {
                return Response.status(Response.Status.BAD_REQUEST).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Conflicto de Jornada").build();
            }

            entity.setIdPruebaJornadaAulaAspiranteOpcion(UUID.randomUUID());
            entity.setIdPruebaJornada(prueba);
            entity.setIdJornadaAula(aula);
            entity.setIdAspiranteOpcion(aspirante);
            
            pruebaJornadaAulaAspiranteOpcionDAO.crear(entity);
            return Response.created(uriInfo.getAbsolutePathBuilder().path(entity.getIdPruebaJornadaAulaAspiranteOpcion().toString()).build()).entity(entity).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorRango(
            @PathParam("idPruebaJornada") UUID idPrueba,
            @PathParam("idJornadaAula") UUID idAula,
            @QueryParam("first") @DefaultValue("0") int first,
            @QueryParam("max") @DefaultValue("50") int max) {

        if (idPrueba == null || idAula == null || first < 0 || max <= 0 || max > 50) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Parámetros inválidos").build();
        }

        try {
            List<PruebaJornadaAulaAspiranteOpcion> registros = pruebaJornadaAulaAspiranteOpcionDAO.buscarPorPruebaJornadaYJornadaAulaRango(idPrueba, idAula, first, max);
            Long total = pruebaJornadaAulaAspiranteOpcionDAO.contarPorPruebaJornadaYJornadaAula(idPrueba, idAula);
            return Response.ok(registros).header(ResponseHeaders.TOTAL_RECORDS.toString(), total).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(
            @PathParam("idPruebaJornada") UUID idPrueba,
            @PathParam("idJornadaAula") UUID idAula,
            @PathParam("id") UUID id) {

        if (idPrueba == null || idAula == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs requeridos").build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcion encontrado = pruebaJornadaAulaAspiranteOpcionDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPrueba, idAula);
            return (encontrado != null) ? Response.ok(encontrado).build() : Response.status(404).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("idPruebaJornada") UUID idPrueba,
            @PathParam("idJornadaAula") UUID idAula,
            @PathParam("id") UUID id,
            PruebaJornadaAulaAspiranteOpcion entity) {

        if (idPrueba == null || idAula == null || entity == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Datos incompletos").build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcion existente = pruebaJornadaAulaAspiranteOpcionDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPrueba, idAula);
            if (existente == null) return Response.status(404).build();

            // Lógica simplificada de AspiranteOpcion
            if (entity.getIdAspiranteOpcion() != null && entity.getIdAspiranteOpcion().getIdAspiranteOpcion() != null) {
                AspiranteOpcion aspirante = aspiranteOpcionDAO.buscarPorId(entity.getIdAspiranteOpcion().getIdAspiranteOpcion());
                if (aspirante == null) return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "AspiranteOpcion no encontrada").build();
                entity.setIdAspiranteOpcion(aspirante);
            } else {
                entity.setIdAspiranteOpcion(existente.getIdAspiranteOpcion());
            }

            entity.setIdPruebaJornadaAulaAspiranteOpcion(id);
            entity.setIdPruebaJornada(existente.getIdPruebaJornada());
            entity.setIdJornadaAula(existente.getIdJornadaAula());
            
            pruebaJornadaAulaAspiranteOpcionDAO.actualizar(entity);
            return Response.ok(entity).build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(
            @PathParam("idPruebaJornada") UUID idPrueba,
            @PathParam("idJornadaAula") UUID idAula,
            @PathParam("id") UUID id) {

        if (idPrueba == null || idAula == null || id == null) {
            return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "IDs faltantes").build();
        }

        try {
            PruebaJornadaAulaAspiranteOpcion existente = pruebaJornadaAulaAspiranteOpcionDAO.buscarPorIdYPruebaJornadaYJornadaAula(id, idPrueba, idAula);
            if (existente == null) return Response.status(404).build();
            
            pruebaJornadaAulaAspiranteOpcionDAO.eliminar(existente);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }
}