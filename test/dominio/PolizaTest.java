/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emilio
 */
public class PolizaTest {

    Poliza polizaTestedMensual;
    Poliza polizaTestedTrimestral;
    Poliza polizaTestedSemestral;
    Poliza polizaTestedAnual;

    public PolizaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Asegurado asegurado = new Asegurado("emilio", "hernandez", "segovia");
        polizaTestedMensual = new Poliza("456789", "GNP", asegurado.getId(), "autos", "producto", "plan", LocalDate.now(), "agente", "mensual", new Dinero(BigDecimal.valueOf(78945.21f), "pesos"));
        polizaTestedTrimestral = new Poliza("456789", "GNP", asegurado.getId(), "autos", "producto", "plan", LocalDate.now(), "agente", "trimestral", new Dinero(BigDecimal.valueOf(78945.21f), "pesos"));
        polizaTestedSemestral = new Poliza("456789", "GNP", asegurado.getId(), "autos", "producto", "plan", LocalDate.now(), "agente", "semestral", new Dinero(BigDecimal.valueOf(78945.21f), "pesos"));
        polizaTestedAnual = new Poliza("456789", "GNP", asegurado.getId(), "autos", "producto", "plan", LocalDate.now(), "agente", "anual", new Dinero(BigDecimal.valueOf(78945.21f), "pesos"));

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGenerarRecibos() {
        int recibosPagados = 3;
        float derechoPoliza = 123;
        float subsecuentes = 25;
        polizaTestedMensual.generarRecibos(recibosPagados, derechoPoliza, subsecuentes);
        polizaTestedTrimestral.generarRecibos(recibosPagados, derechoPoliza, subsecuentes);
        polizaTestedSemestral.generarRecibos(recibosPagados, derechoPoliza, subsecuentes);
        polizaTestedAnual.generarRecibos(recibosPagados, derechoPoliza, subsecuentes);

//        System.out.println(polizaTestedMensual.getRecibos());
        System.out.println("MENSUAL ===================");
        polizaTestedMensual.getRecibos().stream().forEach(r -> System.out.println(r));
        System.out.println("TRIEMSTRAL ===================");
        polizaTestedTrimestral.getRecibos().stream().forEach(r -> System.out.println(r));
        System.out.println("SEMESTRAL ===================");
        polizaTestedSemestral.getRecibos().stream().forEach(r -> System.out.println(r));
        System.out.println("ANUAL ===================");
        polizaTestedAnual.getRecibos().stream().forEach(r -> System.out.println(r));
    }

    @Test
    public void testGetOptionalAttributes() {
        try {
//            BigDecimal value = polizaTestedAnual.getDeducible().map(Dinero::getCantidad).get();
            String val = polizaTestedAnual.getDeducible().isPresent() ? polizaTestedAnual.getDeducible().get().getMoneda().toString() : null;
            val = polizaTestedAnual.getDeducible().map(Dinero::getMoneda).map(Enum::toString).orElse(null);
            System.out.println(val);
//            System.out.println(polizaTestedAnual.getDeducible().isPresent() ? polizaTestedAnual.getDeducible().map(Dinero::getMoneda).map(Enum::toString) : null);
//            System.out.println(polizaTestedAnual.getDeducible().map(Dinero::getMoneda).orElse(null));
//            System.out.println(polizaTestedAnual.getDeducible().map(Dinero::getMoneda).map(Enum::toString).orElse(null));
//            System.out.println(polizaTestedAnual.getCoaseguro().map(Integer::shortValue).orElse(null));
//            short valshort = polizaTestedAnual.getCoaseguro().map(Integer::shortValue).orElse(null);
            Optional<Short> optint = Optional.empty();
            short sh = optint.orElse(Short.MIN_VALUE);
//            short valshort = polizaTestedAnual.getCoaseguro().map(Integer::shortValue).orElse(null);
            System.out.println(val);
            polizaTestedAnual.setDeducible(new Dinero(BigDecimal.ONE, Dinero.Moneda.PESOS));
            System.out.println(polizaTestedAnual.getDeducible().isPresent() ? polizaTestedAnual.getDeducible().map(Dinero::getMoneda).map(Enum::toString) : null);
            System.out.println(polizaTestedAnual.getDeducible().isPresent() ? polizaTestedAnual.getDeducible().get().getMoneda() : null);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        BigDecimal value = polizaTestedAnual.getDeducible().map(Dinero::getCantidad).orElse(BigDecimal.TEN);
    }

    /**
     * Test of getNumero method, of class Poliza.
     */
//    @Test
//    public void testGetNumero() {
//        System.out.println("getNumero");
//        Poliza instance = null;
//        String expResult = "";
//        String result = instance.getNumero();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAseguradora method, of class Poliza.
//     */
//    @Test
//    public void testGetAseguradora() {
//        System.out.println("getAseguradora");
//        Poliza instance = null;
//        String expResult = "";
//        String result = instance.getAseguradora();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getContratante method, of class Poliza.
//     */
//    @Test
//    public void testGetContratante() {
//        System.out.println("getContratante");
//        Poliza instance = null;
//        Asegurado expResult = null;
//        Asegurado result = instance.getContratante();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRamo method, of class Poliza.
//     */
//    @Test
//    public void testGetRamo() {
//        System.out.println("getRamo");
//        Poliza instance = null;
//        Poliza.Ramo expResult = null;
//        Poliza.Ramo result = instance.getRamo();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getProducto method, of class Poliza.
//     */
//    @Test
//    public void testGetProducto() {
//        System.out.println("getProducto");
//        Poliza instance = null;
//        String expResult = "";
//        String result = instance.getProducto();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPlan method, of class Poliza.
//     */
//    @Test
//    public void testGetPlan() {
//        System.out.println("getPlan");
//        Poliza instance = null;
//        String expResult = "";
//        String result = instance.getPlan();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getInicioVigencia method, of class Poliza.
//     */
//    @Test
//    public void testGetInicioVigencia() {
//        System.out.println("getInicioVigencia");
//        Poliza instance = null;
//        LocalDate expResult = null;
//        LocalDate result = instance.getInicioVigencia();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFinVigencia method, of class Poliza.
//     */
//    @Test
//    public void testGetFinVigencia() {
//        System.out.println("getFinVigencia");
//        Poliza instance = null;
//        LocalDate expResult = null;
//        LocalDate result = instance.getFinVigencia();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getConductoDeCobro method, of class Poliza.
//     */
//    @Test
//    public void testGetConductoDeCobro() {
//        System.out.println("getConductoDeCobro");
//        Poliza instance = null;
//        Poliza.ConductoDeCobro expResult = null;
//        Poliza.ConductoDeCobro result = instance.getConductoDeCobro();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFormaDePago method, of class Poliza.
//     */
//    @Test
//    public void testGetFormaDePago() {
//        System.out.println("getFormaDePago");
//        Poliza instance = null;
//        Poliza.FormaDePago expResult = null;
//        Poliza.FormaDePago result = instance.getFormaDePago();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPrima method, of class Poliza.
//     */
//    @Test
//    public void testGetPrima() {
//        System.out.println("getPrima");
//        Poliza instance = null;
//        Dinero expResult = null;
//        Dinero result = instance.getPrima();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRecibos method, of class Poliza.
//     */
//    @Test
//    public void testGetRecibos() {
//        System.out.println("getRecibos");
//        Poliza instance = null;
//        List<Recibo> expResult = null;
//        List<Recibo> result = instance.getRecibos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDeducible method, of class Poliza.
//     */
//    @Test
//    public void testGetDeducible() {
//        System.out.println("getDeducible");
//        Poliza instance = null;
//        Optional<Dinero> expResult = null;
//        Optional<Dinero> result = instance.getDeducible();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getSumaAsegurada method, of class Poliza.
//     */
//    @Test
//    public void testGetSumaAsegurada() {
//        System.out.println("getSumaAsegurada");
//        Poliza instance = null;
//        Optional<Dinero> expResult = null;
//        Optional<Dinero> result = instance.getSumaAsegurada();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCoaseguro method, of class Poliza.
//     */
//    @Test
//    public void testGetCoaseguro() {
//        System.out.println("getCoaseguro");
//        Poliza instance = null;
//        Optional<Integer> expResult = null;
//        Optional<Integer> result = instance.getCoaseguro();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getComentarios method, of class Poliza.
//     */
//    @Test
//    public void testGetComentarios() {
//        System.out.println("getComentarios");
//        Poliza instance = null;
//        Optional<String> expResult = null;
//        Optional<String> result = instance.getComentarios();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setDeducible method, of class Poliza.
//     */
//    @Test
//    public void testSetDeducible() {
//        System.out.println("setDeducible");
//        Dinero deducible = null;
//        Poliza instance = null;
//        instance.setDeducible(deducible);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setSumaAsegurada method, of class Poliza.
//     */
//    @Test
//    public void testSetSumaAsegurada() {
//        System.out.println("setSumaAsegurada");
//        Dinero sumaAsegurada = null;
//        Poliza instance = null;
//        instance.setSumaAsegurada(sumaAsegurada);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setCoaseguro method, of class Poliza.
//     */
//    @Test
//    public void testSetCoaseguro() {
//        System.out.println("setCoaseguro");
//        Integer coaseguro = null;
//        Poliza instance = null;
//        instance.setCoaseguro(coaseguro);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setComentarios method, of class Poliza.
//     */
//    @Test
//    public void testSetComentarios() {
//        System.out.println("setComentarios");
//        String comentarios = "";
//        Poliza instance = null;
//        instance.setComentarios(comentarios);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
    @Test
    public void testDeterminaRamo() {
        System.out.println("determinaRamo");
        Poliza poliza = new Poliza("autos");
        assertEquals(Poliza.Ramo.AUTOS, poliza.getRamo());
        System.out.println(poliza.getRamo());
        poliza = new Poliza("AUTOS");
        assertEquals(Poliza.Ramo.AUTOS, poliza.getRamo());

    }
}
