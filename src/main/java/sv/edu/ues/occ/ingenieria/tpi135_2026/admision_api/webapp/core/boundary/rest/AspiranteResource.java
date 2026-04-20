package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.*;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Aspirante;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Prueba;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaJornadaAulaAspiranteOpcionExamen;

@Path("aspirante")
public class AspiranteResource implements Serializable {

    @Inject
    AspiranteDAO aspiranteDAO;

    @Inject
    IngresoUniversitarioPrimeraRondaStrategy ingresoUniversitarioPrimeraRondaStrategy;

    @Inject
    IngresoUniversitarioSegundaRondaStrategy ingresoUniversitarioSegundaRondaStrategy;

    @Inject
    ExamenDefaultStrategy examenDefaultStrategy;

    @Inject
    PruebaJornadaAulaAspiranteOpcionExamenDAO pruebaJornadaAulaAspiranteOpcionExamenDAO;

    @Inject
    PruebaDAO pruebaDAO;

    Map<String, ExamenStrategy> estrategiasEstado;

    @PostConstruct
    public void inicializar() {
        estrategiasEstado = new HashMap<>();
        estrategiasEstado.put(TipoPruebaEnum.INGRESO_UNIVERSITARIO_PRIMERA_RONDA.name(), ingresoUniversitarioPrimeraRondaStrategy);
        estrategiasEstado.put(TipoPruebaEnum.INGRESO_UNIVERSITARIO_SEGUNDA_RONDA.name(), ingresoUniversitarioSegundaRondaStrategy);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crear(Aspirante aspirante, @Context UriInfo uriInfo) {

        if (aspirante != null && aspirante.getIdAspirante() == null) {
            try {
                aspirante.setIdAspirante(UUID.randomUUID());
                aspiranteDAO.crear(aspirante);
                UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                uriBuilder.path(aspirante.getIdAspirante().toString());
                return Response.created(uriBuilder.build()).entity(aspirante).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El recurso no puede ser nulo y no debe tener un ID asignado").build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") UUID id) {
        if (id != null) {
            try {
                Aspirante aspirante = aspiranteDAO.buscarPorId(id);
                if (aspirante != null) {
                    aspiranteDAO.eliminar(aspirante);
                    return Response.noContent().build();
                }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El ID no puede ser nulo").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response buscarPorRango(
            @QueryParam("first")
            @DefaultValue("0")
            int firstResult,
            @QueryParam("max")
            @DefaultValue("50")
            int maxResults) {
        try {
            if (firstResult >= 0 && maxResults > 0 && maxResults <= 50) {
                List<Aspirante> aspirantes = aspiranteDAO.buscarPorRango(firstResult, maxResults);
                Long total = aspiranteDAO.contar();
                Response.ResponseBuilder responseBuilder = Response.ok(aspirantes)
                        .header(ResponseHeaders.TOTAL_RECORDS.toString(), total)
                        .type(MediaType.APPLICATION_JSON);
                return responseBuilder.build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Los parámetros 'first' debe ser >= 0 y 'max' debe ser > 0 y <= 50").build();
            }

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") UUID id) {
        if (id != null) {
            try {
                Aspirante encontrado = aspiranteDAO.buscarPorId(id);
                if (encontrado != null) {
                    Response.ResponseBuilder builder = Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            }catch (Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).header(ResponseHeaders.WRONG_PARAMETER.toString(), "id: "+id).build();
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") UUID id, Aspirante aspirante) {
        if (aspirante != null && id != null) {
            try {
                Aspirante existente = aspiranteDAO.buscarPorId(id);
                if (existente != null) {
                    aspirante.setIdAspirante(id);
                    aspiranteDAO.actualizar(aspirante);
                    return Response.ok(aspirante).build();
                }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El recurso no puede ser nulo y debe tener un ID asignado").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id_aspirante}/prueba/{id_prueba}")
    public Response listarResultadoExamen(@PathParam("id_aspirante") UUID idAspirante, @PathParam("id_prueba") UUID idPrueba) {
        if (idAspirante == null || idPrueba == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(), "Los IDs de aspirante y prueba no pueden ser nulos")
                    .build();
        }
        try {
            PruebaJornadaAulaAspiranteOpcionExamen examen = pruebaJornadaAulaAspiranteOpcionExamenDAO
                    .obtenerResultadoExamenPorAspiranteYPrueba(idAspirante, idPrueba);
            if (examen == null || examen.getIdPrueba() == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "Resultado no encontrado para el aspirante y prueba especificados")
                        .build();
            }
            Prueba prueba = examen.getIdPrueba();
            String tipoPruebaValor = prueba.getIdTipoPrueba().getValor();
            ExamenStrategy estrategiaAplicar = estrategiasEstado.getOrDefault(tipoPruebaValor, examenDefaultStrategy);
            ExamenResultadosEnum estadoExamen = estrategiaAplicar.obtenerEstado(prueba.getNotaAprobacion(), examen.getResultado());
            return Response.ok(estadoExamen).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id_aspirante}/pruebas")
    public Response listarPruebasPorAspirante(@PathParam("id_aspirante") UUID idAspirante){
        if(idAspirante == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ResponseHeaders.WRONG_PARAMETER.toString(), "El ID de aspirante no puede ser nulo")
                    .build();
        }
        try{
            List<Prueba> pruebas = pruebaDAO.findByIdAspirante(idAspirante);
            if(pruebas == null || pruebas.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND)
                        .header(ResponseHeaders.NOT_FOUND.toString(), "No se encontraron pruebas para el aspirante especificado")
                        .build();
            }
            return Response.status(Response.Status.OK).entity(pruebas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage())
                    .build();
        }

    }



}