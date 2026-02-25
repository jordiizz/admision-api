package sv.edu.ues.occ.ingenieria.tpi135_2026.boundary.rest;

import java.io.Serializable;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import static sv.edu.ues.occ.ingenieria.tpi135_2026.boundary.rest.ResponseHeaders.PROCESS_ERROR;
import static sv.edu.ues.occ.ingenieria.tpi135_2026.boundary.rest.ResponseHeaders.WRONG_PARAMETER;
import sv.edu.ues.occ.ingenieria.tpi135_2026.control.AreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.entity.Area;

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
                return Response.status(500).header(PROCESS_ERROR.toString(), "No se pudo crear el recurso").build();
            } catch (Exception e) {
                return Response.status(500).header(PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(422).header(WRONG_PARAMETER.toString(), "El recurso no puede ser nulo y no debe tener un ID asignado").build();
    }
}
