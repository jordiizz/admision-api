package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;

@Path("area")
public class AreaResource  implements Serializable {

    @Inject
    AreaDAO areaDAO;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crear(Area area, @Context UriInfo uriInfo) {

        if (area != null && area.getIdArea() == null) {
            try {
                areaDAO.crear(area);
                if (area.getIdArea() != null) {
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(area.getIdArea().toString());
                    return Response.created(uriBuilder.build()).entity(area).build();
                }
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), "No se pudo crear el recurso").build();
            } catch (Exception e) {
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El recurso no puede ser nulo y no debe tener un ID asignado").build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        if (id != null) {
            try {
                Area area = areaDAO.buscarPorId(id);
                if (area != null) {
                    areaDAO.eliminar(area);
                    return Response.noContent().build();
                }
                return Response.status(404).header(ResponseHeaders.NOT_FOUND.toString(), "Recurso no encontrado").build();
            } catch (Exception e) {
                return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "El ID no puede ser nulo").build();
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
                List<Area> areas = areaDAO.buscarPorRango(firstResult, maxResults);
                Long total = areaDAO.contar();
                Response.ResponseBuilder responseBuilder = Response.ok(areas)
                        .header("Total_Records", total)
                        .type(MediaType.APPLICATION_JSON);
                return responseBuilder.build();
            } else {
                return Response.status(422).header(ResponseHeaders.WRONG_PARAMETER.toString(), "Los parámetros 'first' debe ser >= 0 y 'max' debe ser > 0 y <= 50").build();
            }

        }catch (Exception e){
            return Response.status(500).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
        }
    }
}
