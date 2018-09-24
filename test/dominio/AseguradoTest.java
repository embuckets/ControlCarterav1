/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AseguradoTest {

    Asegurado aseguradoTested;

    public AseguradoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        aseguradoTested = new Asegurado("Emilio", "Hernandez", "Segovia");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Asegurado.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = new Asegurado("Emilio", "Hernandez", "Segovia");
        assertEquals(true, aseguradoTested.equals(o));
        
        o = new Asegurado("emilio", "hernandez", "segovia");
        assertEquals(true, aseguradoTested.equals(o));
        
        o = new Asegurado("emilio", "hernández", "segovia");
        assertEquals(false, aseguradoTested.equals(o));
        
        o = new Asegurado("procesos automatizados s.a de c.v", null, null);
        aseguradoTested = new Asegurado("procesos automatizados s.a de c.v", null, null);
        assertEquals(true, aseguradoTested.equals(o));
        
        o = new Asegurado("procesos automatizados s.a de c.v", "", "");
        assertEquals(true, aseguradoTested.equals(o));
        
        o = new Asegurado("niño", null, null);
        aseguradoTested = new Asegurado("niño", null, null);
        assertEquals(true, aseguradoTested.equals(o));
        
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testListContains(){
        System.out.println("testListContains");
        List<Asegurado> asegurados = new ArrayList<Asegurado>();
        asegurados.add(aseguradoTested);
        System.out.println("index de emilio: " + asegurados.indexOf(aseguradoTested));
        
        Map<String, Asegurado> mapaAsegurados = new HashMap<String, Asegurado>();
        mapaAsegurados.put(aseguradoTested.getNombre(), aseguradoTested);
        System.out.println("antes\n" + mapaAsegurados);
        
        //TODO: ERROR emilio hernandez segovia -> daniel hernandez segovia
        mapaAsegurados.get(aseguradoTested.getNombre()).setNombre("daniel");
        mapaAsegurados.remove("Emilio");
        mapaAsegurados.put(aseguradoTested.getNombre(), aseguradoTested);
        Asegurado ase2 = new Asegurado("gabriel", "", "");
        mapaAsegurados.put(ase2.getNombre(), ase2);
        System.out.println("despues\n" + mapaAsegurados);
    }

//    /**
//     * Test of getNombre method, of class Asegurado.
//     */
//    @Test
//    public void testGetNombre() {
//        System.out.println("getNombre");
//        Asegurado instance = null;
//        String expResult = "";
//        String result = instance.getNombre();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getApellidoPaterno method, of class Asegurado.
//     */
//    @Test
//    public void testGetApellidoPaterno() {
//        System.out.println("getApellidoPaterno");
//        Asegurado instance = null;
//        Optional<String> expResult = null;
//        Optional<String> result = instance.getApellidoPaterno();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getApellidoMaterno method, of class Asegurado.
//     */
//    @Test
//    public void testGetApellidoMaterno() {
//        System.out.println("getApellidoMaterno");
//        Asegurado instance = null;
//        Optional<String> expResult = null;
//        Optional<String> result = instance.getApellidoMaterno();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTelefonos method, of class Asegurado.
//     */
//    @Test
//    public void testGetTelefonos() {
//        System.out.println("getTelefonos");
//        Asegurado instance = null;
//        List<String> expResult = null;
//        List<String> result = instance.getTelefonos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCorreos method, of class Asegurado.
//     */
//    @Test
//    public void testGetCorreos() {
//        System.out.println("getCorreos");
//        Asegurado instance = null;
//        List<String> expResult = null;
//        List<String> result = instance.getCorreos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRFC method, of class Asegurado.
//     */
//    @Test
//    public void testGetRFC() {
//        System.out.println("getRFC");
//        Asegurado instance = null;
//        Optional<String> expResult = null;
//        Optional<String> result = instance.getRFC();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setRFC method, of class Asegurado.
//     */
//    @Test
//    public void testSetRFC() {
//        System.out.println("setRFC");
//        String rfc = "";
//        Asegurado instance = null;
//        instance.setRFC(rfc);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of agregarCorreo method, of class Asegurado.
//     */
//    @Test
//    public void testAgregarCorreo() {
//        System.out.println("agregarCorreo");
//        String correo = "";
//        Asegurado instance = null;
//        instance.agregarCorreo(correo);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of agregarTelefono method, of class Asegurado.
//     */
//    @Test
//    public void testAgregarTelefono() {
//        System.out.println("agregarTelefono");
//        String telefono = "";
//        Asegurado instance = null;
//        instance.agregarTelefono(telefono);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of agregarPoliza method, of class Asegurado.
//     */
//    @Test
//    public void testAgregarPoliza() {
//        System.out.println("agregarPoliza");
//        Poliza poliza = null;
//        Asegurado instance = null;
//        instance.agregarPoliza(poliza);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }

}
