package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngresoUniversitarioSegundaRondaStrategyTest {

    IngresoUniversitarioSegundaRondaStrategy cut =  new IngresoUniversitarioSegundaRondaStrategy();

    @Test
    public void testObtenerResultadoNotaAprobacionNull(){
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerEstado(null, new BigDecimal(30));
        });
        assertEquals("nota de aprobación y resultado no pueden ser nulos y la prueba de ingreso debe ser 30", e.getMessage());
    }

    @Test
    public void testObtenerResultadoResultadoNull(){
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            cut.obtenerEstado(new BigDecimal(30), null);
        });
        assertEquals("nota de aprobación y resultado no pueden ser nulos y la prueba de ingreso debe ser 30", e.getMessage());
    }

    @Test
    public void testObtenerResultadoSeleccionado(){
        ExamenResultadosEnum resultado = cut.obtenerEstado(new BigDecimal(50), new BigDecimal(50));
        assertEquals(ExamenResultadosEnum.SELECCIONADO, resultado);
    }

    @Test
    public void testObtenerResultadoReprobado(){
        ExamenResultadosEnum resultado = cut.obtenerEstado(new BigDecimal(50), new BigDecimal(30));
        assertEquals(ExamenResultadosEnum.REPROBADO, resultado);
    }


}