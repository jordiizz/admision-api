package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Path("procesarlento")
public class ProcesarLentoResource implements Serializable {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CompletableFuture<Long> procesar(){
        return CompletableFuture.supplyAsync(this::contar);
    }

    public Long contar(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        return System.currentTimeMillis();
    }

}
