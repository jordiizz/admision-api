package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExamenDefaultStrategyTest {

    ExamenDefaultStrategy cut =  new ExamenDefaultStrategy();

    @Test
    public void testObtenerResultadoNotaAprobacionNull(){
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerEstado(null, new BigDecimal(30));
        });
        assertEquals("nota de aprobación y resultado no pueden ser nulos", e.getMessage());
    }

    @Test
    public void testObtenerResultadoResultadoNull(){
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerEstado(new BigDecimal(30), null);
        });
        assertEquals("nota de aprobación y resultado no pueden ser nulos", e.getMessage());
    }

    @Test
    public void testObtenerResultadoAprobado(){
        ExamenResultadosEnum resultado = cut.obtenerEstado(new BigDecimal(50), new BigDecimal(50));
        assertEquals(ExamenResultadosEnum.APROBADO, resultado);
    }



    @Test
    public void testObtenerResultadoReprobado(){
        ExamenResultadosEnum resultado = cut.obtenerEstado(new BigDecimal(50), new BigDecimal(29));
        assertEquals(ExamenResultadosEnum.REPROBADO, resultado);
    }
}