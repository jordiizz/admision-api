package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaAulaDAO;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.JornadaAula;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.Jornada;
import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control.JornadaDAO;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;


@Path("jornada/{id_jornada}/aula")
public class JornadaAulaResource implements Serializable {
    
    @Inject
    JornadaAulaDAO jADAO;
    
    @Inject
    JornadaDAO jDAO;

    /**
     * LISTA LAS AULAS ASOCIADAS A UNA JORNADA ESPECÍFICA
     * @param idJornada
     * @return LA LISTA DE AULAS ASOCIADAS A LA JORNADA
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAulaJornadas(@PathParam("id_jornada") UUID idJornada){
        if(idJornada != null){
            try {
                List<JornadaAula> aulas = jADAO.listarPorJornada(idJornada);
                if(aulas != null && !aulas.isEmpty()){
                    return Response.status(Response.Status.OK).entity(aulas).build();
                } 
                return Response.status(Response.Status.NOT_FOUND).entity("aulas").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * ELIMINA UN AULA ASOCIADA A UNA JORNADA ESPECÍFICA
     * @param idJornada EL ID DE LA JORNADA DE LA QUE SE DESEA ELIMINAR EL AULA
     * @param idAula EL ID DEL AULA QUE SE DESEA ELIMINAR
     * @return
     */
    @DELETE
    @Path("/{id_aula}")
    public Response eliminar(@PathParam("id_jornada") UUID idJornada, @PathParam("id_aula") String idAula){

        if(idJornada != null && idAula != null){    
            try {
                JornadaAula jA = jADAO.buscarPorJornadaYAula(idJornada, idAula);
                if(jA != null){
                    jADAO.eliminar(jA);
                    return Response.status(Response.Status.NO_CONTENT).build();
                } 
                return Response.status(Response.Status.NOT_FOUND).entity("No se encontró el aula para la jornada especificada.").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    /**
     * ASOCIA UN AULA A UNA JORNADA ESPECÍFICA
     * @param idJornada EL ID DE LA JORNADA A LA QUE SE DESEA ASOCIAR EL AULA
     * @return 
     */
    @POST
    @Path("/{id_aula}")
    public Response crear(@PathParam("id_jornada") UUID idJornada, @PathParam("id_aula") String idAula){
        if(idJornada != null && idAula != null){
            try {
                Jornada j = jDAO.buscarPorId(idJornada);
                if(j != null){
                    JornadaAula jA = new JornadaAula(UUID.randomUUID());
                    jA.setIdAula(idAula);
                    jA.setIdJornada(j);
                    jADAO.crear(jA);
                    return Response.status(Response.Status.CREATED).entity(jA).build();
                }
                return Response.status(Response.Status.NOT_FOUND).header(ResponseHeaders.NOT_FOUND.toString(), "jornada").build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(ResponseHeaders.PROCESS_ERROR.toString(), e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();}
}
