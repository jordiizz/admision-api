package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;

@ApplicationScoped
public class IngresoUniversitarioSegundaRondaStrategy implements ExamenStrategy{

    @Override
    public ExamenResultadosEnum obtenerEstado(BigDecimal notaAprobración, BigDecimal resultado) {
        if(notaAprobración != null && resultado != null){
            if(resultado.compareTo(notaAprobración) >= 0) {
                return ExamenResultadosEnum.SELECCIONADO;
            }
            return ExamenResultadosEnum.REPROBADO;
        }
       throw new IllegalArgumentException("nota de aprobación y resultado no pueden ser nulos y la prueba de ingreso debe ser 30");
    }
}
