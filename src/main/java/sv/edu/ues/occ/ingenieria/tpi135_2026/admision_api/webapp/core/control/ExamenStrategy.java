package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.math.BigDecimal;

public interface ExamenStrategy {

    ExamenResultadosEnum obtenerEstado(BigDecimal notaAprobracion, BigDecimal resultado);
    
}
