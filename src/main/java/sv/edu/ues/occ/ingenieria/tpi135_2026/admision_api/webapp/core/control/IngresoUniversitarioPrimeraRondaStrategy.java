package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import java.math.BigDecimal;

public class IngresoUniversitarioPrimeraRondaStrategy implements ExamenStrategy{

    private final BigDecimal NOTA_MINIMA_SEGUNDA_RONDA = new BigDecimal(30);

    @Override
    public ExamenResultadosEnum obtenerEstado(BigDecimal notaAprobración, BigDecimal resultado){
        if(notaAprobración != null && resultado != null){
            if(resultado.compareTo(notaAprobración) >= 0) {
                return ExamenResultadosEnum.SELECCIONADO;
            } else if (resultado.compareTo(NOTA_MINIMA_SEGUNDA_RONDA) >= 0) {
                return ExamenResultadosEnum.SEGUNDA_RONDA;
            }
            return ExamenResultadosEnum.REPROBADO;
        }
       throw new IllegalArgumentException("nota de aprobación y resultado no pueden ser nulos");
    }
}
