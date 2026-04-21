package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Provider
public class CorseResponseFilter implements ContainerResponseFilter {

    public static final String METODOS_PERMITIDOS="GET, POST, PUT, DELETE, OPTIONS, HEAD";
    public static final int MAXIMO_CACHE=30*60*60;
    public static final String CABECERAS_PERMITIDAS="origin,accept,content-type";
    public static final String CABECERAS_EXPUESTAS="location,info";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", getRequestAllowHeaders(responseContext));
        headers.add("Access-Control-Expose-Headers", CABECERAS_PERMITIDAS);
    }

    String getRequestAllowHeaders(ContainerResponseContext responseContext) {
        List<Object> headers = responseContext.getHeaders().get("Access-Control-Allow-Headers");
        return crearCabeceras(headers, CABECERAS_PERMITIDAS);
    }

    String crearCabeceras(List<Object> cabeceras, String cabecerasPorDefecto){

        List<String> salidas = new ArrayList<>();

        if (cabeceras==null || cabeceras.isEmpty()){
            return cabecerasPorDefecto;
        }
        StringBuilder sb = new StringBuilder();
        for (Object cabecera : cabeceras){
            sb.append(cabecera.toString());
            sb.append(";");
        }
        sb.append(cabecerasPorDefecto);
        return sb.toString();
    }
}
