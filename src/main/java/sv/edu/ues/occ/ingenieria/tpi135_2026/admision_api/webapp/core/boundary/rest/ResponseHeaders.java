package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.boundary.rest;

/**
 * Enum que define los headers HTTP personalizados utilizados en las respuestas de la API REST.
 */
public enum ResponseHeaders {
    
    PROCESS_ERROR("Process-Error"),
    WRONG_PARAMETER("Wrong-Parameter"),
    NOT_FOUND("Not-Found"),
    TOTAL_RECORDS("Total-Records"),
    VIOLATES_BUSINESS_RULES("Violates-Business-Rules");
    
    private final String headerName;
    
    ResponseHeaders(String headerName) {
        this.headerName = headerName;
    }
    
    public String getHeaderName() {
        return headerName;
    }
    
    @Override
    public String toString() {
        return headerName;
    }
}