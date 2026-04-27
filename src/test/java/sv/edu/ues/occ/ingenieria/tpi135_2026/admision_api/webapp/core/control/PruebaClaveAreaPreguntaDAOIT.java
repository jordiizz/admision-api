    package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.control;

    import org.junit.jupiter.api.*;
    import sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity.*;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.OffsetDateTime;
    import java.util.List;
    import java.util.UUID;

    import static org.junit.jupiter.api.Assertions.*;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class PruebaClaveAreaPreguntaDAOIT extends AbstractIntengrationDAOTest{

        PruebaClaveAreaPreguntaDAO cut = new PruebaClaveAreaPreguntaDAO();

        TipoPruebaDAO tipoPruebaDAO = new TipoPruebaDAO();
        PruebaDAO pruebaDAO = new PruebaDAO();
        PruebaClaveDAO pruebaClaveDAO = new PruebaClaveDAO();
        AreaDAO areaDAO = new AreaDAO();
        PreguntaAreaDAO preguntaAreaDAO = new PreguntaAreaDAO();
        PreguntaDAO preguntaDAO = new PreguntaDAO();
        PruebaClaveAreaDAO pruebaClaveAreaDAO = new PruebaClaveAreaDAO();

        TipoPrueba tipoPrueba = new TipoPrueba(UUID.randomUUID());
        Prueba prueba = new Prueba(UUID.randomUUID());
        PruebaClave pruebaClave = new PruebaClave(UUID.randomUUID());
        PruebaClave pruebaClave2 = new PruebaClave(UUID.randomUUID());
        Area area = new Area(UUID.randomUUID());
        Pregunta pregunta = new Pregunta(UUID.randomUUID());
        Pregunta pregunta2 = new Pregunta(UUID.randomUUID());
        PreguntaArea preguntaArea = new PreguntaArea(pregunta, area);
        PreguntaArea preguntaArea2 = new PreguntaArea(pregunta2, area);

        PruebaClaveAreaPreguntaPK pk = new PruebaClaveAreaPreguntaPK(pruebaClave.getIdPruebaClave(), area.getIdArea(), pregunta.getIdPregunta());
        PruebaClaveAreaPregunta pruebaClaveAreaPregunta = new PruebaClaveAreaPregunta(pruebaClave, area, pregunta);
        PruebaClaveAreaPreguntaPK pk2 = new PruebaClaveAreaPreguntaPK(pruebaClave.getIdPruebaClave(), area.getIdArea(), pregunta2.getIdPregunta());
        PruebaClaveAreaPregunta pruebaClaveAreaPregunta2 = new PruebaClaveAreaPregunta(pruebaClave, area, pregunta2);
        PruebaClaveAreaPregunta pruebaClaveAreaPregunta3 = new PruebaClaveAreaPregunta(pruebaClave2, area, pregunta);

        PruebaClaveArea pruebaClaveArea = new PruebaClaveArea(pruebaClave, area);

        @BeforeEach
        public void setUp(){
            tipoPruebaDAO.em = em;
            pruebaDAO.em = em;
            areaDAO.em = em;
            pruebaClaveDAO.em = em;
            preguntaDAO.em = em;
            preguntaAreaDAO.em =em;
            cut.em = em;
            pruebaClaveAreaDAO.em = em;

            tipoPrueba.setValor("INGRESO_UNIVERSITARIO_PRIMERA_RONDA");
            tipoPrueba.setActivo(true);

            prueba.setNombre("NUEVO_INGRESO_2026");
            prueba.setNotaAprobacion(new BigDecimal(60));
            prueba.setIndicaciones("Aprobada mayor 60, menor a este y mayor o igual a 30 pasa a segunda ronda");
            prueba.setFechaCreacion(OffsetDateTime.now());
            prueba.setIdTipoPrueba(tipoPrueba);
            prueba.setPuntajeMaximo(new BigDecimal(100));
            prueba.setDuracion(120);

            pruebaClave.setIdPrueba(prueba);
            pruebaClave.setNombreClave("PRIMERA_CLAVE");

            area.setNombre("MATEMATICAS");
            area.setActivo(true);
            area.setDescripcion("Ciencia de los números y sus relaciones");

            pregunta.setValor("¿Cuánto es 2 + 2?");
            pregunta.setActivo(true);

            pregunta2.setValor("¿Cuánto es 4 * 2?");
            pregunta2.setActivo(true);

            pruebaClaveAreaPregunta.setPorcentaje(new BigDecimal(1));
            pruebaClaveAreaPregunta2.setPorcentaje(new BigDecimal(1));

            pruebaClaveAreaPregunta3.setPorcentaje(new BigDecimal(0));

        }

        public void crearContexto(){
            em.getTransaction().begin();
            tipoPruebaDAO.crear(tipoPrueba);
            pruebaDAO.crear(prueba);
            pruebaClaveDAO.crear(pruebaClave);
            areaDAO.crear(area);
            preguntaDAO.crear(pregunta);
            preguntaDAO.crear(pregunta2);
            preguntaAreaDAO.crear(preguntaArea);
            preguntaAreaDAO.crear(preguntaArea2);
            pruebaClaveAreaDAO.crear(pruebaClaveArea);
            em.getTransaction().commit();
        }

        @Order(1)
        @Test
        public void testCrear(){
            crearContexto();
            em.getTransaction().begin();
            Long registros = cut.contar();
            cut.crear(pruebaClaveAreaPregunta);
            cut.crear(pruebaClaveAreaPregunta2);
            Long registrosDespues = cut.contar();
            em.getTransaction().commit();
            assertTrue(registrosDespues > registros);
        }

        @Order(2)
        @Test
        public void testBuscarPorId(){
            PruebaClaveAreaPregunta encontrado = cut.buscarPorId(pk);
            assertEquals(encontrado, pruebaClaveAreaPregunta);
        }

        @Order(3)
        @Test
        public void testBuscarPorRango(){
            List<PruebaClaveAreaPregunta> encontrados = cut.buscarPorRango(0, 10);
            assertTrue(encontrados.size() >= 2);
        }

        @Order(4)
        @Test
        public void testBuscarPorClaveYArea(){
            List<PruebaClaveAreaPregunta> encontrados = cut.buscarPorClaveYArea(pruebaClave.getIdPruebaClave(), area.getIdArea());
            assertTrue(encontrados.contains(pruebaClaveAreaPregunta));
            assertTrue(encontrados.contains(pruebaClaveAreaPregunta2));
            assertEquals(2, encontrados.size());
        }

        @Order(5)
        @Test
        public void testActualizar(){
            BigDecimal porcentaje = new BigDecimal(2);
            pruebaClaveAreaPregunta.setPorcentaje(porcentaje);
            PruebaClaveAreaPregunta actualizado = cut.actualizar(pruebaClaveAreaPregunta);
            assertEquals(porcentaje,actualizado.getPorcentaje());
        }

        @Order(6)
        @Test
        public void testEliminar(){
            em.getTransaction().begin();
            Long registros = cut.contar();
            cut.eliminar(pruebaClaveAreaPregunta);
            PruebaClaveAreaPregunta encontrado = cut.buscarPorId(pk);
            Long registrosDespues = cut.contar();
            em.getTransaction().commit();
            assertNull(encontrado);
            assertTrue(registrosDespues < registros);
        }

        @Order(7)
        @Test
        public void testEliminarNoContained() {
            cut.crear(pruebaClaveAreaPregunta);
            em.clear();
            cut.eliminar(pruebaClaveAreaPregunta);
            PruebaClaveAreaPregunta encontrado = cut.buscarPorId(pk);
            assertNull(encontrado);
        }

        @Order(8)
        @Test
        public void testCrearEmNull(){
            cut.em = null;
            try{
                cut.crear(pruebaClaveAreaPregunta2);
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(9)
        @Test
        public void testCrearParametrosInvalidos(){
            try{
                cut.crear(null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(10)
        @Test
        public void testBuscarPorClaveYAreaIdPruebaClaveNull(){
            try{
                cut.buscarPorClaveYArea(null, area.getIdArea());
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(11)
        @Test
        public void testBuscarPorClaveYAreaIdAreaNull(){
            try{
                cut.buscarPorClaveYArea(pruebaClave.getIdPruebaClave(), null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(12)
        @Test
        public void testBuscarPorClaveYAreaBothNull(){
            try{
                cut.buscarPorClaveYArea(null, null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(13)
        @Test
        public void testBuscarPorClaveYAreaEmNull(){
            cut.em = null;
            try{
                cut.buscarPorClaveYArea(pruebaClave.getIdPruebaClave(), area.getIdArea());
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(14)
        @Test
        public void testBuscarPorRangoEmNull(){
            cut.em = null;
            try{
                cut.buscarPorRango(0, 10);
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(15)
        @Test
        public void testBuscarPorRangoFirstNegativo(){
            try{
                cut.buscarPorRango(-1, 10);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(16)
        @Test
        public void testBuscarPorRangoMaxNegativo(){
            try{
                cut.buscarPorRango(0, -1);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(17)
        @Test
        public void testBuscarPorIdEmNull(){
            cut.em = null;
            try{
                cut.buscarPorId(pk);
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(18)
        @Test
        public void testBuscarPorIdParametroNull(){
            try{
                cut.buscarPorId(null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(19)
        @Test
        public void testEliminarEmNull(){
            cut.em = null;
            try{
                cut.eliminar(pruebaClaveAreaPregunta);
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(20)
        @Test
        public void testEliminarParametroNull(){
            try{
                cut.eliminar(null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(21)
        @Test
        public void testActualizarEmNull(){
            cut.em = null;
            try{
                cut.actualizar(pruebaClaveAreaPregunta);
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(22)
        @Test
        public void testActualizarParametroNull(){
            try{
                cut.actualizar(null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(23)
        @Test
        public void testContarEmNull(){
            cut.em = null;
            try{
                cut.contar();
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(24)
        @Test
        public void testValidarPorcentajePruebaValido(){

            boolean resultado = cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta2);

            assertTrue(resultado, "El porcentaje total (2) no debe exceder el máximo (100)");
        }

        @Order(25)
        @Test
        public void testValidarPorcentajePruebaExactamente(){
            em.getTransaction().begin();

            Pregunta pregunta4 = new Pregunta(UUID.randomUUID());
            pregunta4.setValor("¿Cuánto es 10 * 10?");
            pregunta4.setActivo(true);
            preguntaDAO.crear(pregunta4);

            PreguntaArea preguntaArea4 = new PreguntaArea(pregunta4, area);
            preguntaAreaDAO.crear(preguntaArea4);

            PruebaClaveAreaPregunta pruebaClaveAreaPregunta5 = new PruebaClaveAreaPregunta(pruebaClave, area, pregunta4);
            pruebaClaveAreaPregunta5.setPorcentaje(new BigDecimal(98)); // Suma parcial = 2 + 98 = 100

            em.getTransaction().commit();

            // Act: Validar que 98 cabe exactamente (2 + 98 = 100)
            boolean resultado = cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta5);

            assertTrue(resultado, "El porcentaje total (100) debe ser igual al máximo (100)");
        }

        @Order(26)
        @Test
        public void testValidarPorcentajePruebaExcedido(){
            em.getTransaction().begin();

            Pregunta pregunta3 = new Pregunta(UUID.randomUUID());
            pregunta3.setValor("¿Cuánto es 8 / 2?");
            pregunta3.setActivo(true);
            preguntaDAO.crear(pregunta3);

            PreguntaArea preguntaArea3 = new PreguntaArea(pregunta3, area);
            preguntaAreaDAO.crear(preguntaArea3);

            PruebaClaveAreaPregunta pruebaClaveAreaPregunta3 = new PruebaClaveAreaPregunta(pruebaClave, area, pregunta3);
            pruebaClaveAreaPregunta3.setPorcentaje(new BigDecimal(99));
            cut.crear(pruebaClaveAreaPregunta3);

            em.getTransaction().commit();

            PruebaClaveAreaPregunta pruebaClaveAreaPregunta4 = new PruebaClaveAreaPregunta(pruebaClave, area, pregunta);
            pruebaClaveAreaPregunta4.setPorcentaje(new BigDecimal(10)); // 99 + 10 = 109 > 100

            boolean resultado = cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta4);

            assertFalse(resultado, "El porcentaje total (109) debe exceder el máximo (100)");
        }

        @Order(27)
        @Test
        public void testValidarPorcentajePruebaPruebaClaveNull(){

            try{
                cut.validarPorcentajePrueba(null, pruebaClaveAreaPregunta);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(28)
        @Test
        public void testValidarPorcentajePruebaAreaPreguntaNull(){
            try{
                cut.validarPorcentajePrueba(pruebaClave, null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(29)
        @Test
        public void testValidarPorcentajePruebaBothNull(){
            try{
                cut.validarPorcentajePrueba(null, null);
            } catch (Exception ex) {
                assertEquals(IllegalArgumentException.class, ex.getClass());
            }
        }

        @Order(30)
        @Test
        public void testValidarPorcentajePruebaEmNull(){
            cut.em = null;
            try{
                cut.validarPorcentajePrueba(pruebaClave, pruebaClaveAreaPregunta);
            } catch (Exception ex) {
                assertEquals(IllegalStateException.class, ex.getClass());
            }
        }

        @Order(31)
        @Test
        public void testValidarPorcentajeCero() {

            em.getTransaction().begin();

            PruebaClave pruebaClaveNueva = new PruebaClave(UUID.randomUUID());
            pruebaClaveNueva.setNombreClave("CLAVE_SIN_PREGUNTAS");
            pruebaClaveNueva.setIdPrueba(prueba); // Usamos la 'prueba' del setUp (Puntaje Máximo = 100)

            pruebaClaveDAO.crear(pruebaClaveNueva);
            em.getTransaction().commit();

            PruebaClaveAreaPregunta nuevoRegistro = new PruebaClaveAreaPregunta();
            nuevoRegistro.setPorcentaje(new BigDecimal(50));

            boolean resultado = cut.validarPorcentajePrueba(pruebaClaveNueva, nuevoRegistro);

            assertTrue(resultado, "Debe permitir el registro cuando aún no hay porcentajes acumulados (suma inicial es null)");

            nuevoRegistro.setPorcentaje(new BigDecimal(101));
            boolean resultadoExcedido = cut.validarPorcentajePrueba(pruebaClaveNueva, nuevoRegistro);
            assertFalse(resultadoExcedido, "Debe fallar si el primer registro ya excede el puntaje máximo");
        }

    }
