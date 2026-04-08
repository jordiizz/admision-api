package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.AreaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PreguntaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveAreaPreguntaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.PruebaClaveDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Area;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Pregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClave;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPregunta;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.PruebaClaveAreaPreguntaPK;


@Path("prueba_clave/{id_prueba_clave}/area/{id_area}/pregunta")
public class PruebaClaveAreaPreguntaResource implements Serializable{

    @Inject
    PreguntaDAO pDAO;

    @Inject
    AreaDAO aDAO;

    @Inject
    PruebaClaveDAO pCDAO;

    @Inject
    PruebaClaveAreaPreguntaDAO pCAPDAO;

    @POST
    @Path("{id_pregunta}")
    public Response crear(@PathParam("id_prueba_clave") UUID idPruebaClave,
                    @PathParam("id_area") UUID idArea, @PathParam("id_pregunta") UUID idPregunta) {

        if(idPruebaClave != null && idArea != null && idPregunta != null) {
            try {
                
                Pregunta p = pDAO.buscarPorId(idPregunta);
                Area a = aDAO.buscarPorId(idArea);
                PruebaClave pC = pCDAO.buscarPorId(idPruebaClave);
                if(p != null && a != null && pC != null) {
                    PruebaClaveAreaPregunta pruebaClaveAreaPregunta = new PruebaClaveAreaPregunta(pC.getIdPruebaClave(), a.getIdArea(), p.getIdPregunta()); // Debería recibir entidades no?
                    pCAPDAO.crear(pruebaClaveAreaPregunta);
                    return Response.status(Response.Status.CREATED).build();
                }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(),"pruebaClave, area o pregunta").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id_pregunta}")
    public Response eliminar(@PathParam("id_prueba_clave") UUID idPruebaClave,
                    @PathParam("id_area") UUID idArea, @PathParam("id_pregunta") UUID idPregunta) {

        if(idPruebaClave != null && idArea != null && idPregunta != null) {

            try {
                PruebaClaveAreaPreguntaPK pk = new PruebaClaveAreaPreguntaPK(idPruebaClave, idArea, idPregunta);
                PruebaClaveAreaPregunta pruebaClaveAreaPregunta = pCAPDAO.buscarPorId(pk);
                if(pruebaClaveAreaPregunta != null) {
                    pCAPDAO.eliminar(pruebaClaveAreaPregunta);
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(),"pruebaClave, area o pregunta").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    @GET
    public Response listar(@PathParam("id_prueba_clave") UUID idPruebaClave,
                    @PathParam("id_area") UUID idArea) {

        if(idPruebaClave != null && idArea != null ) {
            try {
                List<PruebaClaveAreaPregunta> pruebaClaveAreaPreguntas = pCAPDAO.buscarPorClaveYPregunta(idPruebaClave, idArea);
               if(pruebaClaveAreaPreguntas != null){
                    return Response.status(Response.Status.OK).entity(pruebaClaveAreaPreguntas).build();
               }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(),"pruebaClave, area ").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }
}