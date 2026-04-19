package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.math.BigDecimal;

public class ExamenDefaultStrategy implements ExamenStrategy{

    @Override
    public ExamenResultadosEnum obtenerEstado(BigDecimal notaAprobración, BigDecimal resultado) {

        if(notaAprobración != null && resultado != null){
            if(resultado.compareTo(notaAprobración) >= 0) {
                return ExamenResultadosEnum.APROBADO;
            }
            return ExamenResultadosEnum.REPROBADO;
        }
        throw new IllegalArgumentException("nota de aprobación y resultado no pueden ser nulos");
    }
}
